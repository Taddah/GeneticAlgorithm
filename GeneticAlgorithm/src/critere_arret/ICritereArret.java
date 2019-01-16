package critere_arret;

import copiable.ICopiable;
import modeles.Population;

/** ICritereArret Design pattern Strategy + Prototype pour permettre de cloner la classe en cas de 
 *  multithreading sur l'algorithme genetique
 *
 */
public interface ICritereArret extends ICopiable<ICritereArret>{

	/**
	 * Permet de savoir si les conditions d'arrêts sont atteintes 
	 * 
	 * @param pop population sur laquelle appliquer le critère d'arrêt
	 * @return True si l'algorithme est sensé s'arrêter
	 */
	boolean algorithmeDoitStopper(Population pop);
}
