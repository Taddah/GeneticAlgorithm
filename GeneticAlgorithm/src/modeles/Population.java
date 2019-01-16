package modeles;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import debug.DebugLogger;

/**
 * Une population est une liste d'individu
 * Cette classe permet de gérer cette liste
 *
 */
public class Population {
	
	// Par défaut, au moins 2/3 des individus doivent être "unique"
	public static final int MINIMUM_DIVERSIFICATION_POPULATION = 3; 
	
	private int populationSize;
	private List<IIndividu> individus;

	public Population(int populationSize, IIndividu individu) {
		this.populationSize = populationSize;
		this.individus = new ArrayList<>();
		
		//Constuire la population
		for(int i = 0; i < this.populationSize; i++) {
			
			IIndividu newIndividu = individu.genererIndividu();
			individus.add(newIndividu);
		}
		
	}
	
	/**
	 * Permet de savoir si la population est suffisamment diversifié
	 * (c'est à dire au moins 2/3 des individus sont différets)
	 * @return boolean
	 */
	public boolean isPopulationDiversified() {
		int sameIndividu = 0;
		boolean resultat = true;
		
		for(int i = 0; i < this.populationSize - 1; i++) {
			if(this.individus.get(i).equals(this.individus.get(i+1))) {
				sameIndividu++;
			}
		}
		
		if(sameIndividu > (this.populationSize / MINIMUM_DIVERSIFICATION_POPULATION)) {
			resultat = false;
		}
		
		return resultat;
	}

	public IIndividu getIndividu(int index) {
		return this.individus.get(index);
	}
	
	public int getPopulationSize() {
		return this.populationSize;
	}

	public List<IIndividu> getIndividus() {
		return this.individus;
	}
	
	public void remplacerIndividu(IIndividu oldIndividu, IIndividu newIndividu) {
		if(!this.individus.contains(oldIndividu)) {
			try {
				DebugLogger.getInstance().printLog(Level.SEVERE, "Erreur : impossible de remplacer l'individu (inexistant");
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				DebugLogger.getInstance().printLog(Level.SEVERE, e.getMessage());
			}
		}
		
		this.individus.remove(oldIndividu);
		this.individus.add(newIndividu);
	}
	
	/**
	 * Retourne le meilleur individu de la population en fonction de sa fitness
	 * @return Individu
	 */
	public IIndividu getTopIndividu() {
		IIndividu topIndividu = this.getIndividus().get(0);
		for(IIndividu ind : this.getIndividus()) {
			if(ind.getFitness() > topIndividu.getFitness()) {
				topIndividu = ind;
			}
		}
		return topIndividu;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Population)) return false;
		
		Population pop = (Population) obj;
		
		if(pop.getPopulationSize() != this.getPopulationSize()) return false;
		
		if(pop.getTopIndividu() != this.getTopIndividu()) return false;
		
		for(int i = 0; i < this.getPopulationSize(); i++) {
			if(!this.getIndividu(i).equals(pop.getIndividu(i))) return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
