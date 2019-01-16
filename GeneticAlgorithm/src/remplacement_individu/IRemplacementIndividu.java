package remplacementIndividu;

import modeles.IIndividu;
import modeles.Population;

/**
 * Déterminer quel individu de la population sera remplacé par un autre
 * Implémentation du design pattern Strategy
 *
 */
public interface IRemplacementIndividu {
	
	/**
	 * @param population
	 * @return Individu qui sera à remplacer
	 */
	IIndividu getIndividuARemplacer(Population pop);
}
