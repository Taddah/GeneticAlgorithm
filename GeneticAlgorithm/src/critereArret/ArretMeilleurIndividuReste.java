package critereArret;

import modeles.Individu;
import modeles.Population;

public class ArretMeilleurIndividuReste implements ICritereArret {

	private Individu currentBestIndividu;
	private int mIterationMax;
	private int mCurrentIteration;
	
	public ArretMeilleurIndividuReste(int maxIteration) {
		this.mIterationMax = maxIteration;
	}
	
	@Override
	public boolean algorithmShouldStop(Population population) {
		
		Individu newBestIndividu = population.getTopIndividu();
		System.out.println("DEBUG ARREt : " + this.mCurrentIteration);
		
		if(this.currentBestIndividu == null) {
			currentBestIndividu = newBestIndividu;
			return false;
		}
		
		if(currentBestIndividu.getFittest() < newBestIndividu.getFittest()) {
			currentBestIndividu = newBestIndividu;
		}
		else {
			if(this.currentBestIndividu.getFittest() == newBestIndividu.getFittest()) {
				System.out.println("SAME");
				this.mCurrentIteration++;
				if(this.mCurrentIteration >= this.mIterationMax) {
					return true;
				}
			}
			else {
				this.mCurrentIteration = 0;
			}
		}
		return false;
	}

}
