package critere_arret;

import java.util.logging.Level;

import debug.DebugLogger;
import modeles.Population;

/**
 * Arr�te l'algorithme quand le temps sp�cifi� est �coul�
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
	 * M�thode sur un thread, incr�mente le compteur mCurrentTime toute les secondes
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
	 *	Permet de d�marrer le compteur (afin d'�viter qu'il se lance directement � la cr�ation de l'objet)
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
