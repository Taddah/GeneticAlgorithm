package critereArret;

import modeles.Population;

/**
 * Arrête l'algorithme quand la population n'évolue plus après le nombre d'itération indiqué
 *
 */
public class ArretEvolutionPopulation implements ICritereArret {

	private Population mOldPopulation;
	private int mIterationMax;
	private int mCurrentIteration;

	public ArretEvolutionPopulation(int iterationMax) {
		this.mIterationMax = iterationMax;
	}

	/* Si la population reste identique durant X itération, retourne true
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
