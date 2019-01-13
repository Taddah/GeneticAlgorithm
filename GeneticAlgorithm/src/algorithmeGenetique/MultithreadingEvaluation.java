package algorithmeGenetique;

import modeles.Individu;

/**
 * Implémentation de Runnable
 * Permet de "créer" des tâches d'évaluations qui seront répartis
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
