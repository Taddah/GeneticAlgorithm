package algorithmeGenetique;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import critereArret.ICritereArret;
import modeles.IIndividu;
import modeles.Population;
import remplacementIndividu.IRemplacementIndividu;
import selectionParents.ISelectionParents;

/**
 * Algorithme g�n�tique, g�re une population  et ses individus en 
 * appenant les m�thodes de croisement. mutation, remplacement etc ...
 * � chaque it�ration
 *
 */
public class AlgorithmeGenetique implements IAlgorithmeGenetique {
	
	/*
	 * Probabilit� de mutation par d�faut (3)
	 */
	public static double DEFAULT_MUTATION_PROB = 3;
	
	/*
	 * Nombre de thread utilis� pour l'�valuation des individus
	 */
	public static int NOMBRE_THREAD_EVALUATION = 1;

	
	/*
	 * D�termin�e par l'utilisateur
	 */
	private int taillePopulation;
	private IIndividu individu;
	private ISelectionParents selectionParent;
	private IRemplacementIndividu remplacementIndividu;
	private ICritereArret critereArret;
	
	private int iterationActuelle;
	private Population population;
	private boolean algorithmIsRunning;

	public AlgorithmeGenetique(int taillePopulation, IIndividu ind, ISelectionParents selectionParents, IRemplacementIndividu remplacementIndividu, ICritereArret critereArret) {
		this.taillePopulation = taillePopulation;
		this.individu = ind;
		this.selectionParent = selectionParents;
		this.remplacementIndividu = remplacementIndividu;
		this.critereArret = critereArret;
		this.iterationActuelle = 0;
		this.algorithmIsRunning = true;

		if(taillePopulation < this.selectionParent.getNombreEnfant()) {
			//Si le nombre enfant ne respecte pas la contrainte, on le met � taillePopulation - 1
			try {
				this.selectionParent.setNombreEnfant(taillePopulation - 1);
				throw new Exception("Erreur : Nombre d'enfant sup�rieur � la taille d�finie de la population (le nombre d'enfant a �t� mis � " + this.selectionParent.getNombreEnfant()+")");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		genererPopulation();
	}
	
	/**
	 * Permet de g�n�rer une population diversifi�e
	 * Si la diversification n'est pas possible, l'utilisateur est averti et l'algorithme arr�t�
	 */
	private void genererPopulation() {
		this.population = new Population(this.taillePopulation, this.individu);
		int tentative = 0;
		
		while(!this.population.isPopulationDiversified()) {
			this.population = new Population(this.taillePopulation, this.individu);
			tentative++;
			
			if(tentative > 100) {
				try {
					throw new Exception("Impossible d'obtenir une population sufisamment diversifi�, revoir la classe impl�mentant Individu.");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		}
	}

	/* 
	 * @see algorithmeGenetique.IAlgorithmeGenetique#iterer(modeles.Population)
	 */
	@Override
	public Population iterer(Population population) {
		iterationActuelle++;

		//2 Evaluer chaque individu (partag� entre plusieurs thread)
		ExecutorService exec = Executors.newFixedThreadPool(NOMBRE_THREAD_EVALUATION);
		List<Future<?>> futures = new ArrayList<Future<?>>(population.getPopulationSize());
		for (IIndividu item : population.getIndividus()) {
			futures.add(exec.submit(new MultithreadingEvaluation(item)));
		}
		for (Future<?> f : futures) {
			try {
				f.get();
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			} catch (ExecutionException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			} 
		}
		exec.shutdown();

		//3 Selection des meilleurs parents
		IIndividu[] parents = this.selectionParent.selectionnerParents(population);


		//4 Croisement & mutation
		Random rand = new Random();
		IIndividu newIndividu = null;
		for(int i = 0; i < this.selectionParent.getNombreEnfant(); i++) {
			//2 parents al�atoire dans l'array
			IIndividu i1 = parents[rand.nextInt(parents.length)];
			IIndividu i2 = parents[rand.nextInt(parents.length)];
			newIndividu = i1.croisement(i2);
			newIndividu.evaluate();

			if(rand.nextInt(100) <= DEFAULT_MUTATION_PROB) {
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
			DEFAULT_MUTATION_PROB = probabilite;
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

	@Override
	public AlgorithmeGenetique clone() throws CloneNotSupportedException {
		return new AlgorithmeGenetique(taillePopulation, individu, selectionParent, remplacementIndividu, critereArret.clone());
	}

	/**
	 * 
	 * @return true si les conditions d'arr�t n'ont pas encore �t� atteinte
	 */
	public boolean isRunning() {
		return this.algorithmIsRunning;
	}

	@Override
	public IIndividu call() throws Exception {
		synchronized(this) {

			if(this.critereArret.algorithmShouldStop(population)) {
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
			NOMBRE_THREAD_EVALUATION = nombreThread;
	}
}
