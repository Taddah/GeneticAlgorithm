package modeles;

/** Mod�le d'un individu, � impl�menter par l'utilisateur
 * 
 *
 */
public interface IIndividu {
	
	/** 
	 * Permet de sp�cifier la m�thode utilis� pour g�n�rer un nouveau Individu
	 * @return Nouveau Individu
	 */
	public IIndividu genererIndividu();
	
	/**
	 * Comparaison d'individu
	 * @param otherIndividu Individu � comparer
	 * @return true si individu identique
	 */
	public boolean equals(Object otherIndividu);
	
	/**
	 * M�thode d'�valuation de l'individu (calcul de la fitness)
	 */
	public void evaluer();
	
	/**
	 * Retourne la fitness calcul� par la m�thode evaluation
	 * @return fitness
	 */
	public int getFitness();
	
	/**
	 * Permet la cr�ation d'un nouveau individu via le croisement de deux autres Individu (parents)
	 * @param otherParent Individu utilis� pour le croisement
	 * @return Nouveau Individu
	 */
	public IIndividu croisement(IIndividu otherParent);
	
	/**
	 * Mutation de l'individu
	 */
	public void mutation();

}
