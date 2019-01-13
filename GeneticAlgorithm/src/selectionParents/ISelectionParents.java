package selectionParents;

import modeles.Individu;
import modeles.Population;

/**
 * Sélection des parents qui serviront pour le croisement d'individu
 * Implémentation du design pattern Strategy
 *
 */
public interface ISelectionParents {

	/**
	 * Retourne un ensemble d'individu répondant aux critères demandés
	 * @param Population dans laquelle effectuer la sélection
	 * @return Array d'individu
	 */
	Individu[] selectionnerParents(Population p);
	
	int getNombreEnfant();
	void setNombreEnfant(int nbEnfant);
}
