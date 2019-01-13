package critereArret;

import modeles.Population;

/** ICritereArret Design pattern Strategy + extension de Cloneable pour permettre de cloner la classe en cas de 
 *  multithreading sur l'algorithme genetique
 *
 */
public interface ICritereArret extends Cloneable{

	/**
	 * Permet de savoir si les conditions d'arr�ts sont atteintes 
	 * 
	 * @param pop population sur laquelle appliquer le crit�re d'arr�t
	 * @return True si l'algorithme est sens� s'arr�ter
	 */
	boolean algorithmShouldStop(Population pop);
	
	/**
	 * Cr�� et retourne une ouvelle instance identique du crit�re d'arr�t
	 * @return ICritereArret
	 */
	ICritereArret clone();
}
