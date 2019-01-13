package selectionParents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import modeles.IIndividu;
import modeles.Population;

/*
 * La sélection des parents se fera au hasard dans la population fournie
 */
public class SelectionParentsAleatoire implements ISelectionParents {

	private int mNombreEnfants;
	
	public SelectionParentsAleatoire(int nombreEnfant) {
		this.mNombreEnfants = nombreEnfant;
	}
	
	@Override
	public IIndividu[] selectionnerParents(Population p) {
		
		
		IIndividu[] parents = new IIndividu[mNombreEnfants + 1];
		Random rand = new Random();
		List<IIndividu> populationCopy = new ArrayList<>(p.getIndividus());
		
		for(int i = 0; i < mNombreEnfants + 1; i++) {
			int individuSelected = rand.nextInt(populationCopy.size());
			parents[i] = populationCopy.get(individuSelected);
			populationCopy.remove(individuSelected);
		}
		
		return parents;
	}
	
	@Override 
	public int getNombreEnfant() {
		return this.mNombreEnfants;
	}

	@Override
	public void setNombreEnfant(int nbEnfant) {
		this.mNombreEnfants = nbEnfant;
		
	}

}
