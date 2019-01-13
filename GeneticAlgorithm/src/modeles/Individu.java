package modeles;

/** Mod�le d'un individu, � impl�menter par l'utilisateur
 * 
 *
 */
public interface Individu {
	
	/** 
	 * Permet de sp�cifier la m�thode utilis� pour g�n�rer un nouveau Individu
	 * @return Nouveau Individu
	 */
	public abstract Individu genererIndividu();
	
	/**
	 * Comparaison d'individu
	 * @param otherIndividu Individu � comparer
	 * @return true si individu identique
	 */
	public abstract boolean equals(Individu otherIndividu);
	
	/**
	 * M�thode d'�valuation de l'individu (calcul de la fitness
	 */
	public abstract void evaluate();
	
	/**
	 * Retourne la fitness calcul� par la m�thode evaluation
	 * @return fitness
	 */
	public abstract int getFitness();
	
	/**
	 * Permet la cr�ation d'un nouveau individu via le croisement de deux autres Individu (parents)
	 * @param otherParent Individu utilis� pour le croisement
	 * @return Nouveau Individu
	 */
	public abstract Individu croisement(Individu otherParent);
	
	/**
	 * Mutation de l'individu
	 */
	public abstract void mutation();

}
