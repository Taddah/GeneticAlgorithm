package modeles;

import java.util.ArrayList;
import java.util.List;

/**
 * Une population est une liste d'individu
 * Cette classe permet de gérer cette liste
 *
 */
public class Population {
	
	private int mPopulationSize;
	private List<IIndividu> mIndividus;

	public Population(int populationSize, IIndividu individu) {
		this.mPopulationSize = populationSize;
		this.mIndividus = new ArrayList<>();
		
		//Constuire la population
		for(int i = 0; i < this.mPopulationSize; i++) {
			
			IIndividu newIndividu = individu.genererIndividu();
			mIndividus.add(newIndividu);
		}
		
	}
	
	/**
	 * Permet de savoir si la population est suffisamment diversifié
	 * (c'est à dire au moins 2/3 des individus sont différets)
	 * @return boolean
	 */
	public boolean isPopulationDiversified() {
		int sameIndividu = 0;
		
		for(int i = 0; i < this.mPopulationSize - 1; i++) {
			if(this.mIndividus.get(i).equals(this.mIndividus.get(i+1))) sameIndividu++;;
		}
		
		if(sameIndividu > (this.mPopulationSize / 3)) return false;
		
		return true;
	}

	public IIndividu getIndividu(int index) {
		return this.mIndividus.get(index);
	}
	
	public int getPopulationSize() {
		return this.mPopulationSize;
	}

	public List<IIndividu> getIndividus() {
		return this.mIndividus;
	}
	
	public void remplacerIndividu(IIndividu oldIndividu, IIndividu newIndividu) {
		if(!this.mIndividus.contains(oldIndividu)) {
			try {
				throw new Exception("Erreur : impossible de remplacer l'individu (inexistant");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.mIndividus.remove(oldIndividu);
		this.mIndividus.add(newIndividu);
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
}
