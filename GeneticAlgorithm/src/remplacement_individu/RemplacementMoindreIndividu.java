package remplacement_individu;

import modeles.IIndividu;
import modeles.Population;

/**
 * Retourne l'individu ayant la moins bonne évaluation afin qu'il soit remplacé
 *
 */
public class RemplacementMoindreIndividu implements IRemplacementIndividu {

	@Override
	public IIndividu getIndividuARemplacer(Population pop) {
		IIndividu individu = pop.getIndividu(0);
		for(IIndividu ind : pop.getIndividus()) {
			if(ind.getFitness() < individu.getFitness()) {
				individu = ind;
			}
		}
		return individu;
	}

}
