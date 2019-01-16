package remplacementIndividu;

import modeles.IIndividu;
import modeles.Population;

/**
 * D�terminer quel individu de la population sera remplac� par un autre
 * Impl�mentation du design pattern Strategy
 *
 */
public interface IRemplacementIndividu {
	
	/**
	 * @param population
	 * @return Individu qui sera � remplacer
	 */
	IIndividu getIndividuARemplacer(Population pop);
}
