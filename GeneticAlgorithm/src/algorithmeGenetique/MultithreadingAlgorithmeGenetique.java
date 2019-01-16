package algorithmeGenetique;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import critereArret.ArretDuree;
import modeles.IIndividu;
import modeles.Resultat;

/**
 * Lancement de plusieurs AlgorithmeGenetique identique sur plusieurs thread
 * avec partage des meilleurs individus � chaque it�ration
 */
public class MultithreadingAlgorithmeGenetique {

	private int nombreThread;
	private IAlgorithmeGenetique algorithme;

	public MultithreadingAlgorithmeGenetique(int nombreThread, IAlgorithmeGenetique algorithmeGenetique) {
		this.nombreThread = nombreThread;
		this.algorithme = algorithmeGenetique;
	}

	/**
	 * Permet de lancer les algorithmes g�n�tiques
	 * @return Resultat de(s) algorithme(s)
	 */
	public Resultat startThreads() {
		long tempsDebut = System.currentTimeMillis();
		
		//1. Cr�ation des algorithmes
		List<Callable<IIndividu>> workers = new ArrayList<>();
		for(int i = 0; i < this.nombreThread; i++) {
			try {
				Callable<IIndividu> worker = this.algorithme.clone();
				
				//D�marrer le compteur de temps si le crit�re d'arr�t est sur la dur�e
				if (((IAlgorithmeGenetique) worker).getCritereArret() instanceof ArretDuree) {
					IAlgorithmeGenetique ag = ((IAlgorithmeGenetique) worker);
					((ArretDuree) ag.getCritereArret()).startCompteur();
				}
				
				workers.add(worker);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
		
		//Tant que les conditions d'arr�t de tous les algorithmes ne sont pas finis, on continue de faire
		//des it�rations dans ceux encore en ex�cutions
		ExecutorService executor = Executors.newFixedThreadPool(this.nombreThread);
		while(!AllAlgorithmHasFInished(workers)) {
			IIndividu topInd = null;
			Callable<IIndividu> topAG = null;
			for (int i = 0; i < this.nombreThread; i++) {
				
				if(((AlgorithmeGenetique) workers.get(i)).isRunning()){
					Future<IIndividu> ind = executor.submit(workers.get(i));

					try {
						if(topInd == null || ind.get().getFitness() > topInd.getFitness()) {
							//On r�cup�re le meilleur individu
							topInd = ind.get();
							topAG = workers.get(i);
						}
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
			}

			//Partage du meilleur individu issue des algorithmes entre tous les algorithmes
			for(Callable<IIndividu> ag : workers) {
				if(ag != topAG) {
					((IAlgorithmeGenetique) ag).remplacerIndividu(topInd);
				}
			}
		}

		//Attente de la fin
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		
		//On r�cup�re la meilleure solution et les diff�rentes donn�es et on les retourne
		// � l'utilisateur
		long tpsExecution = System.currentTimeMillis() - tempsDebut;
		IIndividu solution = getSolution(workers);
		int evaluation = solution.getFitness();
		int nombreIteration = getMaxIteration(workers);
		
		return new Resultat(solution, evaluation, nombreIteration, tpsExecution);
	}

	/**
	 * Permet de savoir si les conditions d'arr�t de chaque algorithmes sont atteintes
	 * @param algos Liste des algorithmes g�n�tiques
	 * @return boolean
	 */
	private boolean AllAlgorithmHasFInished(List<Callable<IIndividu>> algos) {
		for(Callable<IIndividu> ag : algos) if(((IAlgorithmeGenetique) ag).isRunning()) {
			return false;
		}

		return true;
	}
	
	/**
	 * Retourne le meilleur individu (solution) parmis tous les algorithmes
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
	 * Retourne le nombre d'it�ration max parmis tous les algorithmes
	 * @param workers Liste des algorithmes
	 * @return nombre d'it�ration
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
