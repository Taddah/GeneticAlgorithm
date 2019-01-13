package modeles;

/** Modèle d'un individu, à implémenter par l'utilisateur
 * 
 *
 */
public interface Individu {
	
	/** 
	 * Permet de spécifier la méthode utilisé pour générer un nouveau Individu
	 * @return Nouveau Individu
	 */
	public abstract Individu genererIndividu();
	
	/**
	 * Comparaison d'individu
	 * @param otherIndividu Individu à comparer
	 * @return true si individu identique
	 */
	public abstract boolean equals(Individu otherIndividu);
	
	/**
	 * Méthode d'évaluation de l'individu (calcul de la fitness
	 */
	public abstract void evaluate();
	
	/**
	 * Retourne la fitness calculé par la méthode evaluation
	 * @return fitness
	 */
	public abstract int getFitness();
	
	/**
	 * Permet la création d'un nouveau individu via le croisement de deux autres Individu (parents)
	 * @param otherParent Individu utilisé pour le croisement
	 * @return Nouveau Individu
	 */
	public abstract Individu croisement(Individu otherParent);
	
	/**
	 * Mutation de l'individu
	 */
	public abstract void mutation();

}
