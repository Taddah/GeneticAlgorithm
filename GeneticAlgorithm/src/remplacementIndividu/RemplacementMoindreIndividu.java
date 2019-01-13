package remplacementIndividu;

import modeles.Individu;
import modeles.Population;

/**
 * Retourne l'individu ayant la moins bonne �valuation afin qu'il soit remplac�
 *
 */
public class RemplacementMoindreIndividu implements IRemplacementIndividu {

	@Override
	public Individu getIndividuARemplacer(Population pop) {
		Individu individu = pop.getIndividu(0);
		for(Individu ind : pop.getIndividus()) {
			if(ind.getFitness() < individu.getFitness()) {
				individu = ind;
			}
		}
		return individu;
	}

}
