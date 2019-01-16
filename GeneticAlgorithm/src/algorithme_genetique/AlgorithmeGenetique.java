package algorithme_genetique;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import critere_arret.ICritereArret;
import debug.DebugLogger;
import modeles.IIndividu;
import modeles.Population;
import multithreading.MultithreadingEvaluation;
import remplacement_individu.IRemplacementIndividu;
import selection_parents.ISelectionParents;

/**
 * Algorithme génétique, gère une population  et ses individus en 
 * appenant les méthodes de croisement. mutation, remplacement etc ...
 * à chaque itération
 *
 */
public class AlgorithmeGenetique implements IAlgorithmeGenetique {
	
	/*
	 * Probabilité de mutation par défaut (3)
	 */
	private double probabiliteMutationDefaut = 3;
	
	/*
	 * Nombre de thread utilisé pour l'évaluation des individus
	 */
	private int nombreThreadEvaluation = 1;

	
	/*
	 * Déterminée par l'utilisateur
	 */
	private int taillePopulation;
	private IIndividu individu;
	private ISelectionParents selectionParent;
	private IRemplacementIndividu remplacementIndividu;
	private ICritereArret critereArret;
	
	private int iterationActuelle;
	private Population population;
	private boolean algorithmIsRunning;
	private Random rand;

	public AlgorithmeGenetique(int taillePopulation, IIndividu ind, ISelectionParents selectionParents, IRemplacementIndividu remplacementIndividu, ICritereArret critereArret) {
		this.taillePopulation = taillePopulation;
		this.individu = ind;
		this.selectionParent = selectionParents;
		this.remplacementIndividu = remplacementIndividu;
		this.critereArret = critereArret;
		this.iterationActuelle = 0;
		this.algorithmIsRunning = true;
		
		try {
			this.rand = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			DebugLogger.getInstance().printLog(Level.SEVERE, e.getMessage());
		}

		if(taillePopulation < this.selectionParent.getNombreEnfant()) {
			//Si le nombre enfant ne respecte pas la contrainte, on le met à taillePopulation - 1
			try {
				this.selectionParent.setNombreEnfant(taillePopulation - 1);
				DebugLogger.getInstance().printLog(Level.WARNING, "Erreur : Nombre d'enfant supérieur à la taille définie de la population (le nombre d'enfant a été mis à " + this.selectionParent.getNombreEnfant()+")");
			} catch (Exception e) {
				DebugLogger.getInstance().printLog(Level.SEVERE, e.getMessage());
			}
		}
		
		genererPopulation();
	}
	
	/**
	 * Permet de générer une population diversifiée
	 * Si la diversification n'est pas possible, l'utilisateur est averti et l'algorithme arrêté
	 */
	private void genererPopulation() {
		this.population = new Population(this.taillePopulation, this.individu);
		int tentative = 0;
		
		while(!this.population.isPopulationDiversified()) {
			this.population = new Population(this.taillePopulation, this.individu);
			tentative++;
			
			if(tentative > 100) {
				try {
					DebugLogger.getInstance().printLog(Level.SEVERE, "Impossible d'obtenir une population sufisamment diversifié, revoir la classe implémentant Individu.");
				} catch (Exception e) {
					DebugLogger.getInstance().printLog(Level.SEVERE, e.getMessage());
				}
				System.exit(0);
			}
		}
	}

	/* 
	 * @see algorithmeGenetique.IAlgorithmeGenetique#iterer(modeles.Population)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Population iterer(Population population) {
		iterationActuelle++;

		//2 Evaluer chaque individu (partagé entre plusieurs thread)
		ExecutorService exec = Executors.newFixedThreadPool(nombreThreadEvaluation);
		List<Future<MultithreadingEvaluation>> futures = new ArrayList<>(population.getPopulationSize());
		
		for (IIndividu item : population.getIndividus()) {
			Future<?> task =  exec.submit(new MultithreadingEvaluation(item));
			
			if(task instanceof MultithreadingEvaluation) {
				futures.add((Future<MultithreadingEvaluation>) task);
			}
		}
		
		for (Future<?> f : futures) {
			try {
				f.get();
			} catch (InterruptedException | ExecutionException e) {
				DebugLogger.getInstance().printLog(Level.SEVERE, e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
		
		exec.shutdown();

		//3 Selection des meilleurs parents
		IIndividu[] parents = this.selectionParent.selectionnerParents(population);


		//4 Croisement & mutation
		IIndividu newIndividu = null;
		for(int i = 0; i < this.selectionParent.getNombreEnfant(); i++) {
			//2 parents aléatoire dans l'array
			IIndividu i1 = parents[this.rand.nextInt(parents.length)];
			IIndividu i2 = parents[this.rand.nextInt(parents.length)];
			newIndividu = i1.croisement(i2);
			newIndividu.evaluer();

			if(this.rand.nextInt(100) <= probabiliteMutationDefaut) {
				newIndividu.mutation();
			}

			//5 remplacement dans la pop
			remplacerIndividu(newIndividu);
		}
		return population;
	}

	@Override
	public void remplacerIndividu(IIndividu newIndividu) {

		IIndividu individuARemplacer = this.remplacementIndividu.getIndividuARemplacer(this.population);
		this.population.remplacerIndividu(individuARemplacer, newIndividu);
	}

	@Override
	public void setProbabiliteMutation(int probabilite) {
		if(probabilite >= 0 && probabilite <= 100) {
			probabiliteMutationDefaut = probabilite;
		}
	}

	@Override
	public ICritereArret getCritereArret() {
		return this.critereArret;
	}
	
	@Override
	public int getNombreIteration() {
		return this.iterationActuelle;
	}

	/**
	 * 
	 * @return true si les conditions d'arrêt n'ont pas encore été atteinte
	 */
	public boolean isRunning() {
		return this.algorithmIsRunning;
	}

	@Override
	public IIndividu call() throws Exception {
		synchronized(this) {

			if(this.critereArret.algorithmeDoitStopper(population)) {
				this.algorithmIsRunning = false;
			}else {
				iterer(this.population);
			}
			
			return this.population.getTopIndividu();
		}
	}
	
	@Override
	public Population getPopulation() {
		return this.population;
	}

	@Override
	public void setNombreThreadEvaluation(int nombreThread) {
		if(nombreThread > 0)
			nombreThreadEvaluation = nombreThread;
	}

	@Override
	public IAlgorithmeGenetique copie() {
		return new AlgorithmeGenetique(taillePopulation, individu, selectionParent, remplacementIndividu, critereArret.copie());
	}
}
