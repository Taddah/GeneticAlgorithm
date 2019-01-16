package multithreading;

import modeles.IIndividu;

/**
 * Implémentation de Runnable
 * Permet de "créer" des tâches d'évaluations qui seront répartis
 * sur plusieurs threads
 *
 */
public class MultithreadingEvaluation implements Runnable {
	
	private IIndividu individu;
	
	public MultithreadingEvaluation(IIndividu individu) {
		this.individu = individu;
	}

	@Override
	public void run() {
		this.individu.evaluer();
	}

}
