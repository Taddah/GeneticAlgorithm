package modeles;

public abstract class Individu {
	
	public abstract Individu generate();
	
	public abstract boolean equals(Individu otherIndividu);
	
	public abstract void evaluate();
	
	public abstract int getFittest();
	
	public abstract Individu croisement(Individu otherParent);
	
	public abstract void mutation();

}
