package critereArret;

import modeles.Population;

/**
 * Arrête l'algorithme quand le temps spécifié est écoulé
 *
 */
public class ArretDuree implements ICritereArret, Runnable {

	private int mTime;
	private int mCurrentTime;

	public ArretDuree(int timeInSecond) {
		this.mTime = timeInSecond;
		this.mCurrentTime = 0;


	}

	@Override
	public boolean algorithmShouldStop(Population pop) {
		if(this.mCurrentTime >= this.mTime) return true;
		return false;
	}

	/**
	 * Méthode sur un thread, incrémente le compteur mCurrentTime toute les secondes
	 */
	@Override
	public void run() {
		while(!algorithmShouldStop(null)) {
			try {
				Thread.sleep(1000);
				this.mCurrentTime += 1;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	public ICritereArret clone() {
		return new ArretDuree(this.mTime);
	}


}
