package critereArret;

import modeles.Population;

/** ICritereArret Design pattern Strategy + extension de Cloneable pour permettre de cloner la classe en cas de 
 *  multithreading sur l'algorithme genetique
 *
 */
public interface ICritereArret extends Cloneable{

	/**
	 * Permet de savoir si les conditions d'arrêts sont atteintes 
	 * 
	 * @param pop population sur laquelle appliquer le critère d'arrêt
	 * @return True si l'algorithme est sensé s'arrêter
	 */
	boolean algorithmShouldStop(Population pop);
	
	/**
	 * Créé et retourne une ouvelle instance identique du critère d'arrêt
	 * @return ICritereArret
	 */
	ICritereArret clone();
}
