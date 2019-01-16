package algorithme_genetique;

import java.util.concurrent.Callable;

import copiable.ICopiable;
import critere_arret.ICritereArret;
import modeles.IIndividu;
import modeles.Population;

/**
 * Template pour l'algorithme g�n�tique
 * Impl�mentation du design pattern Prototype afin de pouvoir cloner l'algorithme
 *
 */
public interface IAlgorithmeGenetique extends Callable<IIndividu>, ICopiable<IAlgorithmeGenetique> {

	/**
	 * Fais une it�ration sur population
	 * Retourne la nouvelle population ensuite
	 * @param population Population sur laquelle ex�cuter l'algorithme
	 * @return nouvelle population
	 */
	Population iterer(Population population);
	
	/**
	 * 
	 * @param newIndividu Individu � ins�rer dans la population
	 */
	void remplacerIndividu(IIndividu newIndividu);
	
	/**
	 * 
	 * @return Crit�re d'arr�t d�fini par l'utilisateur
	 */
	ICritereArret getCritereArret();
	
	/**
	 * Modifier la probabilit� de mutation d'un individu
	 * @param probabilite
	 */
	void setProbabiliteMutation(int probabilite);
	
	/**
	 * 
	 * @return nombre d'it�ration effectu� 
	 */
	int getNombreIteration();
	
	/**
	 * Impl�mentation de Callable
	 * Permet de faire tourner plusieurs algorithme en m�me temps avec �change des meilleurs individus � chaque it�ration
	 */
	@Override 
	default IIndividu call() throws Exception {
		return null;
	}
	
	/**
	 * 
	 * @return Population
	 */
	Population getPopulation();
	
	/**
	 * @param nombreThread Nombre d'�valuation a effectuer en parall�le
	 */
	void setNombreThreadEvaluation(int nombreThread);

	/**
	 * 
	 * @return L'algorithme est en train de s'ex�cuter ou npn
	 */
	boolean isRunning();
}
