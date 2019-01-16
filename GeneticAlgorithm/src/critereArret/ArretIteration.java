package critereArret;

import modeles.Population;

/**
 * Arr�te l'algorithme quand le nombre d'it�ration demand� est atteint
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
	public boolean algorithmShouldStop(Population pop) {
		this.currentIteration++;
		if(this.currentIteration > this.iterationMax) return true;

		return false;
	}

	@Override
	public ICritereArret clone() {
		return new ArretIteration(this.iterationMax);
	}

}
