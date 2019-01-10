package selectionParents;

import modeles.Individu;
import modeles.Population;

public interface ISelectionParents {

	Individu[] selectionnerParents(Population p);
	int getNombreEnfant();
}
