package algorithmeGenetique;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import critereArret.ArretDuree;
import modeles.Individu;
import modeles.Resultat;

/**
 * Lancement de plusieurs AlgorithmeGenetique identique sur plusieurs thread
 * avec partage des meilleurs individus � chaque it�ration
 */
public class MultithradingAlgorithmeGenetique {

	private int mNombreThread;
	private AlgorithmeGenetique mAlgorithme;

	public MultithradingAlgorithmeGenetique(int nombreThread, AlgorithmeGenetique algorithmeGenetique) {
		this.mNombreThread = nombreThread;
		this.mAlgorithme = algorithmeGenetique;
	}

	/**
	 * Permet de lancer les algorithmes g�n�tiques
	 * @return Resultat de(s) algorithme(s)
	 */
	public Resultat startThreads() {
		long tempsDebut = System.currentTimeMillis();
		
		//1. Cr�ation des algorithmes
		List<Callable<Individu>> workers = new ArrayList<>();
		for(int i = 0; i < this.mNombreThread; i++) {
			try {
				Callable<Individu> worker = this.mAlgorithme.clone();
				if (((AlgorithmeGenetique) worker).getCritereArret() instanceof ArretDuree) {
					AlgorithmeGenetique ag = ((AlgorithmeGenetique) worker);
					((ArretDuree) ag.getCritereArret()).startCompteur();
				}
				workers.add(worker);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
		
		//Tant que les conditions d'arr�t de tous les algorithmes ne sont pas finis, on continue de faire
		//des it�rations dans ceux encore en ex�cutions
		ExecutorService executor = Executors.newFixedThreadPool(this.mNombreThread);
		while(!AllAlgorithmHasFInished(workers)) {
			Individu topInd = null;
			Callable<Individu> topAG = null;
			for (int i = 0; i < this.mNombreThread; i++) {
				
				if(((AlgorithmeGenetique) workers.get(i)).isRunning()){
					Future<Individu> ind = executor.submit(workers.get(i));

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
			for(Callable<Individu> ag : workers) {
				if(ag != topAG) {
					((AlgorithmeGenetique) ag).remplacerIndividu(topInd);
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
		Individu solution = getSolution(workers);
		int evaluation = solution.getFitness();
		int nombreIteration = getMaxIteration(workers);
		
		return new Resultat(solution, evaluation, nombreIteration, tpsExecution);
	}

	/**
	 * Permet de savoir si les conditions d'arr�t de chaque algorithmes sont atteintes
	 * @param algos Liste des algorithmes g�n�tiques
	 * @return boolean
	 */
	private boolean AllAlgorithmHasFInished(List<Callable<Individu>> algos) {
		for(Callable<Individu> ag : algos) if(((AlgorithmeGenetique) ag).isRunning()) {
			return false;
		}

		return true;
	}
	
	/**
	 * Retourne le meilleur individu (solution) parmis tous les algorithmes
	 * @param workers Liste des algorithmes
	 * @return meilleur Individu
	 */
	private Individu getSolution(List<Callable<Individu>> workers) {
		Individu solution = null;
		
		for(Callable<Individu> ag : workers) {
			Individu topInd = ((AlgorithmeGenetique) ag).getPopulation().getTopIndividu();
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
	private int getMaxIteration(List<Callable<Individu>> workers) {
		int maxIteration = 0;
		
		for(Callable<Individu> ag : workers) {
			if(maxIteration < ((AlgorithmeGenetique) ag).getNombreIteration()) {
				maxIteration = ((AlgorithmeGenetique) ag).getNombreIteration();
			}
		}
		
		return maxIteration;
	}
	
	
}
