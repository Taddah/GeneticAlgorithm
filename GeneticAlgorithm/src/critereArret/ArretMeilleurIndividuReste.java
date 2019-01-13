package critereArret;

import modeles.Individu;
import modeles.Population;

/**
 * Arr�te l'algorithme quand le meilleur individu reste identique durant le nombre d'it�ration sp�cifi�
 *
 */
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
