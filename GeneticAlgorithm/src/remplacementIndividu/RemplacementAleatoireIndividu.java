package remplacementIndividu;

import java.util.ArrayList;
import java.util.Random;

import modeles.IIndividu;
import modeles.Population;

/**
 * Retourne de mani�re al�atoire l'individu qui sera remplac�
 * Le meilleur individu ne peut �tre remplac�
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
