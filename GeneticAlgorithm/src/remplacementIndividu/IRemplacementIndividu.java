package remplacementIndividu;

import modeles.Individu;
import modeles.Population;

/**
 * D�terminer quel individu de la population sera remplac� par un autre
 * Impl�mentation du design pattern Strategy
 *
 */
public interface IRemplacementIndividu {
	
	/**
	 * 
	 * @param population
	 * @return Individu qui sera � remplacer
	 */
	Individu getIndividuARemplacer(Population pop);
}
