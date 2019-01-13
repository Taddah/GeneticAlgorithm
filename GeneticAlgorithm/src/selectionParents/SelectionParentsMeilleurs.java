package selectionParents;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import modeles.Individu;
import modeles.Population;

/**
 * La sélection des parents se fera en fonction de leur fitness
 * Ceux avec la meilleur fitness seront retenu
 *
 */
public class SelectionParentsMeilleurs implements ISelectionParents{

private int mNombreEnfants;
	
	public SelectionParentsMeilleurs(int nombreEnfant) {
		this.mNombreEnfants = nombreEnfant;
	}

	@Override
	public Individu[] selectionnerParents(Population p) {
		Individu[] parents = new Individu[mNombreEnfants + 1];
		List<Individu> populationCopy = new ArrayList<>(p.getIndividus());
		
		populationCopy.sort(Comparator.comparing(Individu::getFitness));
		
		for(int i = 0; i < mNombreEnfants + 1; i++) {
			parents[i] = populationCopy.get(i);
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
