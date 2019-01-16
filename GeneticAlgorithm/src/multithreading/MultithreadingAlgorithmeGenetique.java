package multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import algorithme_genetique.AlgorithmeGenetique;
import algorithme_genetique.IAlgorithmeGenetique;
import critere_arret.ArretDuree;
import critere_arret.ICritereArret;
import debug.DebugLogger;
import modeles.IIndividu;
import modeles.Resultat;
import remplacement_individu.IRemplacementIndividu;
import selection_parents.ISelectionParents;

/**
 * Lancement de plusieurs AlgorithmeGenetique identique sur plusieurs thread
 * avec partage des meilleurs individus à chaque itération
 */
public class MultithreadingAlgorithmeGenetique {

	private int nombreThread;
	private IAlgorithmeGenetique algorithme;

	private List<Callable<IIndividu>> workers;

	public MultithreadingAlgorithmeGenetique(int nombreThread, IAlgorithmeGenetique algorithmeGenetique) {
		this.nombreThread = nombreThread;
		this.algorithme = algorithmeGenetique;
		this.workers = new ArrayList<>();
	}
	
	public MultithreadingAlgorithmeGenetique(int nombreThread, int taillePopulation, IIndividu ind, ISelectionParents selectionParents, IRemplacementIndividu remplacementIndividu, ICritereArret critereArret){
		this(nombreThread, new AlgorithmeGenetique(taillePopulation, ind, selectionParents, remplacementIndividu, critereArret));
	}

	/**
	 * Permet de lancer les algorithmes génétiques
	 * @return Resultat de(s) algorithme(s)
	 */
	public Resultat lancerAlgorithmes() {
		long tempsDebut = System.currentTimeMillis();

		//1. Création des algorithmes
		creerAlgorithmes();
		
		//Meilleur individu
		IIndividu meilleurIndividu = null;

		//Tant que les conditions d'arrêt de tous les algorithmes ne sont pas finis, on continue de faire
		//des itérations dans ceux encore en exécutions
		ExecutorService executor = Executors.newFixedThreadPool(this.nombreThread);
		while(!allAlgorithmHasFinished(workers)) {
			
			for (int i = 0; i < this.nombreThread; i++) {

				if(((AlgorithmeGenetique) workers.get(i)).isRunning()){
					
					//Exécution de l'itération
					Future<IIndividu> ind = executor.submit(workers.get(i));
					
					/*Test le meilleur individu de la population de l'algorithme i
					*Si celui ci est moins bon que l'individu enregistré (meilleurIndividu) alors
					*meilleurIndividu est rajouté à la population de l'algorithme i
					*/
					meilleurIndividu = testerIndividu(meilleurIndividu, i, ind);
				}
			}
		}

		//Attente de la fin
		executor.shutdown();

		//On récupère la meilleure solution et les différentes données et on les retourne
		// à l'utilisateur
		long tpsExecution = System.currentTimeMillis() - tempsDebut;
		return envoyerResultat(tpsExecution);
	}

	
	/**
	 * Insère l'individu meilleurIndividu dans l'algorithme si celui ci est meilleur que ceux de l'algorithme
	 * @param meilleurIndividu Meilleur individu de tous les algorithme
	 * @param indexAlgorithme index de l'algorithme actuel dans la liste workers
	 * @param individuAlgorithme meilleur individu de l'algorithme actuel
	 * @return
	 */
	private IIndividu testerIndividu(IIndividu meilleurIndividu, int indexAlgorithme,
			Future<IIndividu> individuAlgorithme) {
		try {
			if(meilleurIndividu == null || individuAlgorithme.get().getFitness() > meilleurIndividu.getFitness()) {
				//On récupère le meilleur individu
				meilleurIndividu = individuAlgorithme.get();
			}
			else if(meilleurIndividu.getFitness() > individuAlgorithme.get().getFitness()) {
				//Si meilleurIndividu est meilleur
				((IAlgorithmeGenetique) workers.get(indexAlgorithme)).remplacerIndividu(meilleurIndividu);
			}
		} catch (InterruptedException | ExecutionException e) {
			DebugLogger.getInstance().printLog(Level.SEVERE, e.getMessage());
			Thread.currentThread().interrupt();
		}
		return meilleurIndividu;
	}

	
	/**
	 * Modifier le nombre de thread qui sera utilisé pour évaluer les individus en parallèle pour chaque algorithme
	 * @param nombreThread Nombre de thread utilisé pour l'évaluation
	 */
	public void setNombreThreadEvaluation(int nombreThread) {
		for(Callable<IIndividu> ag : workers) {
				((IAlgorithmeGenetique) ag).setNombreThreadEvaluation(nombreThread);
		}
	}

	/**
	 * @param tpsExecution Temps d'exécution
	 * @return Resultat de l'aogorithme
	 */
	private Resultat envoyerResultat(long tpsExecution) {
		IIndividu solution = getSolution(workers);

		if(solution != null) {
			int evaluation = solution.getFitness();
			int nombreIteration = getMaxIteration(workers);

			return new Resultat(solution, evaluation, nombreIteration, tpsExecution);
		}
		else {
			return null;
		}
	}

	/**
	 * Création des algorithmes génétique qui seront ensuite exécute en parallèle
	 */
	private void creerAlgorithmes() {
		for(int i = 0; i < this.nombreThread; i++) {
			Callable<IIndividu> worker = this.algorithme.copie();

			//Démarrer le compteur de temps si le critère d'arrêt est sur la durée
			if (((IAlgorithmeGenetique) worker).getCritereArret() instanceof ArretDuree) {
				IAlgorithmeGenetique ag = ((IAlgorithmeGenetique) worker);
				((ArretDuree) ag.getCritereArret()).startCompteur();
			}

			workers.add(worker);
		}
	}

	/**
	 * Permet de savoir si les conditions d'arrêt de chaque algorithmes sont atteintes
	 * @param algos Liste des algorithmes génétiques
	 * @return boolean
	 */
	private boolean allAlgorithmHasFinished(List<Callable<IIndividu>> algos) {
		for(Callable<IIndividu> ag : algos) {
			if(ag instanceof IAlgorithmeGenetique && ((IAlgorithmeGenetique) ag).isRunning()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Retourne le meilleur individu (solution) parmi tous les algorithmes
	 * @param workers Liste des algorithmes
	 * @return meilleur Individu
	 */
	private IIndividu getSolution(List<Callable<IIndividu>> workers) {
		IIndividu solution = null;

		for(Callable<IIndividu> ag : workers) {
			IIndividu topInd = ((IAlgorithmeGenetique) ag).getPopulation().getTopIndividu();
			if(solution == null || solution.getFitness() < topInd.getFitness()) {
				solution = topInd;
			}
		}

		return solution;
	}

	/**
	 * Retourne le nombre d'itération max parmi tous les algorithmes
	 * @param workers Liste des algorithmes
	 * @return nombre d'itération
	 */
	private int getMaxIteration(List<Callable<IIndividu>> workers) {
		int maxIteration = 0;

		for(Callable<IIndividu> ag : workers) {
			if(maxIteration < ((IAlgorithmeGenetique) ag).getNombreIteration()) {
				maxIteration = ((IAlgorithmeGenetique) ag).getNombreIteration();
			}
		}

		return maxIteration;
	}


}
