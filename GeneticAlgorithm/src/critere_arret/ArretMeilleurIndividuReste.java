package critere_arret;

import modeles.IIndividu;
import modeles.Population;

/**
 * Arrête l'algorithme quand le meilleur individu reste identique durant le nombre d'itération spécifié
 *
 */
public class ArretMeilleurIndividuReste implements ICritereArret {

	private IIndividu currentBestIndividu;
	private int iterationMax;
	private int currentIteration;

	public ArretMeilleurIndividuReste(int maxIteration) {
		this.iterationMax = maxIteration;
	}

	@Override
	public boolean algorithmeDoitStopper(Population population) {

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
				this.currentIteration++;
				if(this.currentIteration >= this.iterationMax) {
					return true;
				}
			}
			else {
				this.currentIteration = 0;
			}
		}
		return false;
	}

	@Override
	public ICritereArret copie() {
		return new ArretMeilleurIndividuReste(this.iterationMax);
	}

}
