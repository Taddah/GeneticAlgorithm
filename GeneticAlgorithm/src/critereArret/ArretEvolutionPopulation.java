package critereArret;

import modeles.Population;

public class ArretEvolutionPopulation implements ICritereArret {

	private Population mOldPopulation;
	private int mIterationMax;
	private int mCurrentIteration;
	
	public ArretEvolutionPopulation(int iterationMax) {
		this.mIterationMax = iterationMax;
	}
	
	@Override
	public boolean algorithmShouldStop(Population population) {	
		
		this.mOldPopulation = population;
		if(this.mOldPopulation.equals(population)) {
			this.mCurrentIteration++;
			if(this.mCurrentIteration >= this.mIterationMax) {
				return true;
			}
		}
		else {
			this.mCurrentIteration = 0;
		}
		
		return false;
	}

}
