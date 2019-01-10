package main;

import modeles.Individu;

public class MultithreadingEvaluation implements Runnable {
	
	private Individu mIndividu;
	
	public MultithreadingEvaluation(Individu item) {
		this.mIndividu = item;
	}

	@Override
	public void run() {
		this.mIndividu.evaluate();
	}

}
