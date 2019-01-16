package modeles;

/** Modèle d'un individu, à implémenter par l'utilisateur
 * 
 *
 */
public interface IIndividu {
	
	/** 
	 * Permet de spécifier la méthode utilisé pour générer un nouveau Individu
	 * @return Nouveau Individu
	 */
	public IIndividu genererIndividu();
	
	/**
	 * Comparaison d'individu
	 * @param otherIndividu Individu à comparer
	 * @return true si individu identique
	 */
	public boolean equals(Object otherIndividu);
	
	/**
	 * Méthode d'évaluation de l'individu (calcul de la fitness)
	 */
	public void evaluer();
	
	/**
	 * Retourne la fitness calculé par la méthode evaluation
	 * @return fitness
	 */
	public int getFitness();
	
	/**
	 * Permet la création d'un nouveau individu via le croisement de deux autres Individu (parents)
	 * @param otherParent Individu utilisé pour le croisement
	 * @return Nouveau Individu
	 */
	public IIndividu croisement(IIndividu otherParent);
	
	/**
	 * Mutation de l'individu
	 */
	public void mutation();

}
