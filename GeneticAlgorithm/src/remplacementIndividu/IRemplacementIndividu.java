package remplacementIndividu;

import modeles.Individu;
import modeles.Population;

/**
 * Déterminer quel individu de la population sera remplacé par un autre
 * Implémentation du design pattern Strategy
 *
 */
public interface IRemplacementIndividu {
	
	/**
	 * 
	 * @param population
	 * @return Individu qui sera à remplacer
	 */
	Individu getIndividuARemplacer(Population pop);
}
