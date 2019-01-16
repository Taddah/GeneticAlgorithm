package critere_arret;

import copiable.ICopiable;
import modeles.Population;

/** ICritereArret Design pattern Strategy + Prototype pour permettre de cloner la classe en cas de 
 *  multithreading sur l'algorithme genetique
 *
 */
public interface ICritereArret extends ICopiable<ICritereArret>{

	/**
	 * Permet de savoir si les conditions d'arr�ts sont atteintes 
	 * 
	 * @param pop population sur laquelle appliquer le crit�re d'arr�t
	 * @return True si l'algorithme est sens� s'arr�ter
	 */
	boolean algorithmeDoitStopper(Population pop);
}
