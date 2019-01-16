package algorithmeGenetique;

import java.util.concurrent.Callable;

import critereArret.ICritereArret;
import modeles.IIndividu;
import modeles.Population;

/**
 * Template pour l'algorithme génétique
 * Implémentation du design pattern Prototype afin de pouvoir cloner l'algorithme
 *
 */
public interface IAlgorithmeGenetique extends Callable<IIndividu>, Cloneable {

	/**
	 * Fais une itération sur population
	 * Retourne la nouvelle population ensuite
	 * @param population Population sur laquelle exécuter l'algorithme
	 * @return nouvelle population
	 */
	Population iterer(Population population);
	
	/**
	 * 
	 * @param newIndividu Individu à insérer dans la population
	 */
	void remplacerIndividu(IIndividu newIndividu);
	
	/**
	 * 
	 * @return Critère d'arrêt défini par l'utilisateur
	 */
	ICritereArret getCritereArret();
	
	/**
	 * Modifier la probabilité de mutation d'un individu
	 * @param probabilite
	 */
	void setProbabiliteMutation(int probabilite);
	
	/**
	 * 
	 * @return nombre d'itération effectué 
	 */
	int getNombreIteration();
	
	/**
	 * Implémentation de Callable
	 * Permet de faire tourner plusieurs algorithme en même temps avec échange des meilleurs individus à chaque itération
	 */
	@Override 
	default IIndividu call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Clonage de l'algorithme, utilisé pour en lancer plusieurs en même temps sur différent thread
	 * @return AlgorithmeGenetique
	 * @throws CloneNotSupportedException
	 */
	AlgorithmeGenetique clone() throws CloneNotSupportedException;
	
	/**
	 * 
	 * @return Population
	 */
	Population getPopulation();
	
	/**
	 * @param nombreThread Nombre d'évaluation a effectuer en parallèle
	 */
	void setNombreThreadEvaluation(int nombreThread);

	/**
	 * 
	 * @return L'algorithme est en train de s'exécuter ou npn
	 */
	boolean isRunning();
}
