package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import critereArret.ICritereArret;
import modeles.Individu;
import modeles.Population;
import remplacementIndividu.IRemplacementIndividu;
import selectionParents.ISelectionParents;

public class AlgorithmeGenetique {

	public static double DEFAULT_MUTATION_PROB = 3;
	public static int NOMBRE_THREAD_EVALUATION = 50;

	private int mTaillePopulation;
	private Individu mIndividu;
	private ISelectionParents mSelectionParents;
	private IRemplacementIndividu mRemplacementIndividu;
	private ICritereArret mCritereArret;
	private int mIteration;

	public AlgorithmeGenetique(int taillePopulation, Individu ind, ISelectionParents selectionParents, IRemplacementIndividu remplacementIndividu, ICritereArret critereArret) {
		this.mTaillePopulation = taillePopulation;
		this.mIndividu = ind;
		this.mSelectionParents = selectionParents;
		this.mRemplacementIndividu = remplacementIndividu;
		this.mCritereArret = critereArret;
		this.mIteration = 0;

		//Vérification nombre enfants <= taille population
		if(taillePopulation < this.mSelectionParents.getNombreEnfant()) {
			try {
				throw new Exception("Erreur : Nombre d'enfant supérieur à la taille définie de la population");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void startAlgorithme() {
		//1 créer population
		Population population = new Population(this.mTaillePopulation, this.mIndividu);

		while(!this.mCritereArret.algorithmShouldStop(population)) {

			mIteration++;

			
			//2 Evaluer chaque individu
			ExecutorService exec = Executors.newFixedThreadPool(NOMBRE_THREAD_EVALUATION);
		    List<Future<?>> futures = new ArrayList<Future<?>>(population.getPopulationSize());
		    for (Individu item : population.getIndividus()) {
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

			//3 Selection
			Individu[] parents = this.mSelectionParents.selectionnerParents(population);


			//4 Croisement & mutation
			Random rand = new Random();
			Individu newIndividu = null;
			for(int i = 0; i < this.mSelectionParents.getNombreEnfant(); i++) {
				//2 parents aléatoire dans l'array
				Individu i1 = parents[rand.nextInt(parents.length)];
				Individu i2 = parents[rand.nextInt(parents.length)];
				newIndividu = i1.croisement(i2);
				newIndividu.evaluate();

				if(rand.nextInt(100) <= DEFAULT_MUTATION_PROB) {
					newIndividu.mutation();
				}

				//5 remplacement dans la pop
				remplacerIndividu(newIndividu, population);
			}

			System.out.println("FIN ITERATION n°"+mIteration+" (fittest : " +population.getTopIndividu().getFittest()+")");		
		}

		System.out.println("Fin de l'algo après " + this.mIteration + " itérations");
		System.out.println("Solution trouvé : " + population.getTopIndividu().getFittest());
		System.out.println("Top = " + population.getTopIndividu());
	}

	private void remplacerIndividu(Individu newIndividu, Population population) {

		Individu individuARemplacer = this.mRemplacementIndividu.getIndividuARemplacer(population);
		population.replacerIndividu(individuARemplacer, newIndividu);
	}
	
	public void setProbabiliteMutation(int probabilite) {
		if(probabilite >= 0 && probabilite <= 100) {
			DEFAULT_MUTATION_PROB = probabilite;
		}
	}
}
