package critere_arret;

import modeles.Population;

/**
 * Arrête l'algorithme quand le nombre d'itération demandé est atteint
 *
 *
 */
public class ArretIteration implements ICritereArret {

	private int iterationMax;
	private int currentIteration;

	public ArretIteration(int iterationMax) {
		this.iterationMax = iterationMax;
	}

	
	@Override
	public boolean algorithmeDoitStopper(Population pop) {
		boolean resultat = false;
		
		this.currentIteration++;
		
		if(this.currentIteration > this.iterationMax) resultat = true;

		return resultat;
	}

	@Override
	public ICritereArret copie() {
		return new ArretIteration(this.iterationMax);
	}

}
