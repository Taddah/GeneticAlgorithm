package selection_parents;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import debug.DebugLogger;
import modeles.IIndividu;
import modeles.Population;

/*
 * La sélection des parents se fera au hasard dans la population fournie
 */
public class SelectionParentsAleatoire implements ISelectionParents {

	private int nombreEnfants;
	private Random rand;
	
	public SelectionParentsAleatoire(int nombreEnfant) {
		this.nombreEnfants = nombreEnfant;
		
		try {
			this.rand = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			DebugLogger.getInstance().printLog(Level.SEVERE, e.getMessage());
		}
	}
	
	@Override
	public IIndividu[] selectionnerParents(Population p) {
		
		
		IIndividu[] parents = new IIndividu[nombreEnfants + 1];
		List<IIndividu> populationCopy = new ArrayList<>(p.getIndividus());
		
		for(int i = 0; i < this.nombreEnfants + 1; i++) {
			int individuSelected = this.rand.nextInt(populationCopy.size());
			parents[i] = populationCopy.get(individuSelected);
			populationCopy.remove(individuSelected);
		}
		
		return parents;
	}
	
	@Override 
	public int getNombreEnfant() {
		return this.nombreEnfants;
	}

	@Override
	public void setNombreEnfant(int nbEnfant) {
		this.nombreEnfants = nbEnfant;
		
	}

}
