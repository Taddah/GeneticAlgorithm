package remplacement_individu;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import debug.DebugLogger;
import modeles.IIndividu;
import modeles.Population;

/**
 * Retourne de mani�re al�atoire l'individu qui sera remplac�
 * Le meilleur individu ne peut �tre remplac�
 *
 */
public class RemplacementAleatoireIndividu implements IRemplacementIndividu {

	private Random rand;
	
	public RemplacementAleatoireIndividu() {
		try {
			rand = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			DebugLogger.getInstance().printLog(Level.SEVERE, e.getMessage());
		}
	}
	
	@Override
	public IIndividu getIndividuARemplacer(Population pop) {
		IIndividu topInd = pop.getTopIndividu();
		ArrayList<IIndividu> copyPopulation = new ArrayList<>(pop.getIndividus());
		copyPopulation.remove(topInd);
		
		return copyPopulation.get(rand.nextInt(copyPopulation.size()));	
	}

}
