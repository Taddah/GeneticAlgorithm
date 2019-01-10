package remplacementIndividu;

import java.util.ArrayList;
import java.util.Random;

import modeles.Individu;
import modeles.Population;

public class RemplacementAleatoireIndividu implements IRemplacementIndividu {

	@Override
	public Individu getIndividuARemplacer(Population pop) {
		Individu topInd = pop.getTopIndividu();
		ArrayList<Individu> copyPopulation = new ArrayList<>(pop.getIndividus());
		copyPopulation.remove(topInd);
		
		Random rand = new Random();
		Individu individu = copyPopulation.get(rand.nextInt(copyPopulation.size()));	
		
		return individu;
	}

}
