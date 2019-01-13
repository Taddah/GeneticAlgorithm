package critereArret;

import modeles.IIndividu;
import modeles.Population;

/**
 * Arrête l'algorithme quand le meilleur individu reste identique durant le nombre d'itération spécifié
 *
 */
public class ArretMeilleurIndividuReste implements ICritereArret {

	private IIndividu currentBestIndividu;
	private int mIterationMax;
	private int mCurrentIteration;

	public ArretMeilleurIndividuReste(int maxIteration) {
		this.mIterationMax = maxIteration;
	}

	@Override
	public boolean algorithmShouldStop(Population population) {

		IIndividu newBestIndividu = population.getTopIndividu();

		if(this.currentBestIndividu == null) {
			currentBestIndividu = newBestIndividu;
			return false;
		}

		if(currentBestIndividu.getFitness() < newBestIndividu.getFitness()) {
			currentBestIndividu = newBestIndividu;
		}
		else {
			if(this.currentBestIndividu.getFitness() == newBestIndividu.getFitness()) {
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

	@Override
	public ICritereArret clone()  {
		return new ArretMeilleurIndividuReste(this.mIterationMax);
	}

}
