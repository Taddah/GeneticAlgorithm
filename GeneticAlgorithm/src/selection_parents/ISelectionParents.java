package selectionParents;

import modeles.IIndividu;
import modeles.Population;

/**
 * S�lection des parents qui serviront pour le croisement d'individu
 * Impl�mentation du design pattern Strategy
 *
 */
public interface ISelectionParents {

	/**
	 * Retourne un ensemble d'individu r�pondant aux crit�res demand�s
	 * @param Population dans laquelle effectuer la s�lection
	 * @return Array d'individu
	 */
	IIndividu[] selectionnerParents(Population p);
	
	int getNombreEnfant();
	void setNombreEnfant(int nbEnfant);
}
