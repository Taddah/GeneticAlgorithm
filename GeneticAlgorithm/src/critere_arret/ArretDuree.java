package critere_arret;

import java.util.logging.Level;

import debug.DebugLogger;
import modeles.Population;

/**
 * Arrête l'algorithme quand le temps spécifié est écoulé
 *
 */
public class ArretDuree implements ICritereArret, Runnable {

	private int time;
	private int currentTime;

	public ArretDuree(int timeInSecond) {
		this.time = timeInSecond;
		this.currentTime = 0;


	}

	@Override
	public boolean algorithmeDoitStopper(Population pop) {
		boolean resultat = false;
		if(this.currentTime >= this.time) resultat = true;
		return resultat;
	}

	/**
	 * Méthode sur un thread, incrémente le compteur mCurrentTime toute les secondes
	 */
	@Override
	public void run() {
		while(!algorithmeDoitStopper(null)) {
			try {
				Thread.sleep(1000);
				this.currentTime += 1;
			} catch (InterruptedException e) {
				DebugLogger.getInstance().printLog(Level.SEVERE, e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 *	Permet de démarrer le compteur (afin d'éviter qu'il se lance directement à la création de l'objet)
	 */
	public void startCompteur() {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public ICritereArret copie() {
		return new ArretDuree(this.time);
	}


}
