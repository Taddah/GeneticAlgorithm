package multithreading;

import modeles.IIndividu;

/**
 * Impl�mentation de Runnable
 * Permet de "cr�er" des t�ches d'�valuations qui seront r�partis
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
