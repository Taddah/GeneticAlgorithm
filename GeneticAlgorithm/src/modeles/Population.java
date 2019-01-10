package modeles;

import java.util.ArrayList;
import java.util.List;

public class Population {
	
	private int mPopulationSize;
	private List<Individu> mIndividus;

	public Population(int populationSize, Individu individu) {
		this.mPopulationSize = populationSize;
		this.mIndividus = new ArrayList<>();
		
		//Constuire la population
		for(int i = 0; i < this.mPopulationSize; i++) {
			
			Individu newIndividu = individu.generate();
			mIndividus.add(newIndividu);
		}
		
	}
	
	public boolean isPopulationDiversified() {
		int sameIndividu = 0;
		
		for(int i = 0; i < this.mPopulationSize - 1; i++) {
			if(this.mIndividus.get(i).equals(this.mIndividus.get(i+1))) sameIndividu++;;
		}
		
		System.out.println("Same individu = " + sameIndividu);
		if(sameIndividu > (this.mPopulationSize / 3)) return false;
		
		return true;
	}

	public Individu getIndividu(int index) {
		return this.mIndividus.get(index);
	}
	
	public int getPopulationSize() {
		return this.mPopulationSize;
	}

	public List<Individu> getIndividus() {
		return this.mIndividus;
	}
	
	public void replacerIndividu(Individu oldIndividu, Individu newIndividu) {
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
	
	public Individu getTopIndividu() {
		Individu topIndividu = this.getIndividus().get(0);
		for(Individu ind : this.getIndividus()) {
			if(ind.getFittest() > topIndividu.getFittest()) {
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
		for(int i = 0; i < this.getPopulationSize(); i++) {
			if(!this.getIndividu(i).equals(pop.getIndividu(i))) return false;
		}
		
		return true;
	}
}
