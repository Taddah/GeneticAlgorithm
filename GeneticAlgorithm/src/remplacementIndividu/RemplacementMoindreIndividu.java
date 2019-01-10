package remplacementIndividu;

import modeles.Individu;
import modeles.Population;

public class RemplacementMoindreIndividu implements IRemplacementIndividu {

	@Override
	public Individu getIndividuARemplacer(Population pop) {
		Individu individu = pop.getIndividu(0);
		for(Individu ind : pop.getIndividus()) {
			if(ind.getFittest() < individu.getFittest()) {
				individu = ind;
			}
		}
		return individu;
	}

}
