package selectionParents;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import modeles.IIndividu;
import modeles.Population;

/**
 * La sélection des parents se fera en fonction de leur fitness
 * Ceux avec la meilleur fitness seront retenu
 *
 */
public class SelectionParentsMeilleurs implements ISelectionParents{

private int nombreEnfants;
	
	public SelectionParentsMeilleurs(int nombreEnfant) {
		this.nombreEnfants = nombreEnfant;
	}

	@Override
	public IIndividu[] selectionnerParents(Population p) {
		IIndividu[] parents = new IIndividu[nombreEnfants + 1];
		List<IIndividu> populationCopy = new ArrayList<>(p.getIndividus());
		
		populationCopy.sort(Comparator.comparing(IIndividu::getFitness));
		
		for(int i = 0; i < nombreEnfants + 1; i++) {
			parents[i] = populationCopy.get(i);
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
