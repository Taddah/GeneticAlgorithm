package critereArret;

import modeles.Population;

/**
 * Arr�te l'algorithme quand la population n'�volue plus apr�s le nombre d'it�ration indiqu�
 *
 */
public class ArretEvolutionPopulation implements ICritereArret {

	private Population oldPopulation;
	private int iterationMax;
	private int currentIteration;

	public ArretEvolutionPopulation(int iterationMax) {
		this.iterationMax = iterationMax;
	}

	/* Si la population reste identique durant X it�ration, retourne true
	 * 
	 * @see critereArret.ICritereArret#algorithmShouldStop(modeles.Population)
	 */
	@Override
	public boolean algorithmShouldStop(Population population) {	
		
		if(this.oldPopulation == null)
			this.oldPopulation = population;


		if(this.oldPopulation.equals(population)) {
			this.currentIteration++;
			if(this.currentIteration > this.iterationMax) {
				return true;
			}
		}
		else {
			this.currentIteration = 0;
			this.oldPopulation = null;
		}

		return false;
	}

	@Override
	public ICritereArret clone() {
		return new ArretEvolutionPopulation(this.iterationMax);
	}

}
