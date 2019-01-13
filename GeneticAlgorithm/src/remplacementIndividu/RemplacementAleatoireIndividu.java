package remplacementIndividu;

import java.util.ArrayList;
import java.util.Random;

import modeles.IIndividu;
import modeles.Population;

/**
 * Retourne de manière aléatoire l'individu qui sera remplacé
 * Le meilleur individu ne peut être remplacé
 *
 */
public class RemplacementAleatoireIndividu implements IRemplacementIndividu {

	@Override
	public IIndividu getIndividuARemplacer(Population pop) {
		IIndividu topInd = pop.getTopIndividu();
		ArrayList<IIndividu> copyPopulation = new ArrayList<>(pop.getIndividus());
		copyPopulation.remove(topInd);
		
		Random rand = new Random();
		IIndividu individu = copyPopulation.get(rand.nextInt(copyPopulation.size()));	
		
		return individu;
	}

}
