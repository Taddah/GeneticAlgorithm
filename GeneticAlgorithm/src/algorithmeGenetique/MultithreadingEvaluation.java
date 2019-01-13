package algorithmeGenetique;

import modeles.Individu;

/**
 * Impl�mentation de Runnable
 * Permet de "cr�er" des t�ches d'�valuations qui seront r�partis
 * sur plusieurs threads
 *
 */
public class MultithreadingEvaluation implements Runnable {
	
	private Individu individu;
	
	public MultithreadingEvaluation(Individu individu) {
		this.individu = individu;
	}

	@Override
	public void run() {
		this.individu.evaluate();
	}

}
