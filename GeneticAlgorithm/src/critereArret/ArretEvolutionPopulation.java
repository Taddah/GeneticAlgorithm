package critereArret;

import modeles.Population;

/**
 * Arr�te l'algorithme quand la population n'�volue plus apr�s le nombre d'it�ration indiqu�
 *
 */
public class ArretEvolutionPopulation implements ICritereArret {

	private Population mOldPopulation;
	private int mIterationMax;
	private int mCurrentIteration;

	public ArretEvolutionPopulation(int iterationMax) {
		this.mIterationMax = iterationMax;
	}

	/* Si la population reste identique durant X it�ration, retourne true
	 * 
	 * @see critereArret.ICritereArret#algorithmShouldStop(modeles.Population)
	 */
	@Override
	public boolean algorithmShouldStop(Population population) {	
		
		if(this.mOldPopulation == null)
			this.mOldPopulation = population;


		if(this.mOldPopulation.equals(population)) {
			this.mCurrentIteration++;
			if(this.mCurrentIteration > this.mIterationMax) {
				return true;
			}
		}
		else {
			this.mCurrentIteration = 0;
			this.mOldPopulation = null;
		}

		return false;
	}

	@Override
	public ICritereArret clone() {
		return new ArretEvolutionPopulation(this.mIterationMax);
	}

}
