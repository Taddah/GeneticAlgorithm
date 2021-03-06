package modeles;

/**
 * Classe regroupant les r�sultats de l'algorithme qui sera retourn� � l'utilisateur 
 * � la fin de l'ex�cution
 *
 */
public class Resultat {
	
	private IIndividu solution;
	private int evaluation;
	private int nombreIteration;
	private float tempsExecution;
	
	public Resultat(IIndividu solution, int evaluation, int nombreIteration, long tempsExecution) {
		super();
		this.solution = solution;
		this.evaluation = evaluation;
		this.nombreIteration = nombreIteration;
		this.tempsExecution = tempsExecution / 1000.0f;
	}
	
	public IIndividu getSolution() {
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
