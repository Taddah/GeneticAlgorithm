package critereArret;

import modeles.Population;

public class ArretIteration implements ICritereArret {

	private int mIterationMax;
	private int mCurrentIteration;
	
	public ArretIteration(int iterationMax) {
		this.mIterationMax = iterationMax;
	}
	
	@Override
	public boolean algorithmShouldStop(Population pop) {
		this.mCurrentIteration++;
		if(this.mCurrentIteration > this.mIterationMax) return true;
		
		return false;
	}

}
