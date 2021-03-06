package user_classes;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import modeles.IIndividu;

public class TestIndividu implements IIndividu {
	
	private Random rand;
	private LinkedList<Integer> ind;
	
	private int mFittest;
	
	public TestIndividu() {
		this.rand = new Random();
		this.ind = new LinkedList<>();
	}

	@Override
	public IIndividu genererIndividu() {
		TestIndividu ti = new TestIndividu();
		ti.addToInd(rand.nextInt(5000000));
		ti.addToInd(rand.nextInt(5000000));
		return ti;
	}

	@Override
	public boolean equals(Object otherIndividu) {
		
		boolean resultat = false;
		
		if(otherIndividu instanceof TestIndividu) {
			TestIndividu ind2 = (TestIndividu) otherIndividu;
			
			Object[] arr1 = {this.ind};  // arr1 contains only one element 
	        Object[] arr2 = {ind2.getInd()};  // arr2 also contains only one element 
	        if (Arrays.deepEquals(arr1, arr2)) 
	        	resultat = true;
		}
		
		return resultat;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	public List<Integer> getInd(){
		return this.ind;
	}
	
	public void addToInd(int newValue) {
		this.ind.add(newValue);
	}

	@Override
	public void evaluer() {
		this.mFittest = (this.ind.get(0) * this.ind.get(0)) - (this.ind.get(1) * this.ind.get(1));
	}

	@Override
	public int getFitness() {
		return this.mFittest;
	}

	@Override
	public IIndividu croisement(IIndividu otherParent) {
		TestIndividu newInd = new TestIndividu();
		newInd.addToInd(Math.max(this.getInd().get(0), ((TestIndividu) otherParent).getInd().get(0)));
		newInd.addToInd(Math.min(this.getInd().get(1), ((TestIndividu) otherParent).getInd().get(1)));
		
		return newInd;
	}

	@Override
	public void mutation() {
		this.getInd().set(1, rand.nextInt(999));
		this.getInd().set(0, rand.nextInt(999));
	}
	
	@Override
	public String toString() {
		return "Value="+this.ind.get(0)+"|"+this.ind.get(1);
	}
}
