package modeles;

/**
 * Classe regroupant les résultats de l'algorithme qui sera retourné à l'utilisateur 
 * à la fin de l'exécution
 *
 */
public class Resultat {
	
	private Individu solution;
	private int evaluation;
	private int nombreIteration;
	private float tempsExecution;
	
	public Resultat(Individu solution, int evaluation, int nombreIteration, long tempsExecution) {
		super();
		this.solution = solution;
		this.evaluation = evaluation;
		this.nombreIteration = nombreIteration;
		this.tempsExecution = tempsExecution / 1000.0f;
	}
	
	public Individu getSolution() {
		return solution;
	}

	public int getEvaluation() {
		return evaluation;
	}

	public int getNombreIteration() {
		return nombreIteration;
	}

	public float getTempsExecution() {
		return tempsExecution;
	}

}
