package userClasses;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import modeles.Individu;

public class TestIndividu extends Individu {
	
	private Random rand;
	private Vector<Integer> ind;
	
	private int mFittest;
	
	public TestIndividu() {
		this.rand = new Random();
		this.ind = new Vector<Integer>();
	}

	@Override
	public Individu generate() {
		TestIndividu ti = new TestIndividu();
		ti.addToInd(rand.nextInt(99999));
		ti.addToInd(rand.nextInt(99999));
		return ti;
	}

	@Override
	public boolean equals(Individu otherIndividu) {
		
		if(otherIndividu instanceof TestIndividu) {
			TestIndividu ind2 = (TestIndividu) otherIndividu;
			
			Object[] arr1 = {this.ind};  // arr1 contains only one element 
	        Object[] arr2 = {ind2.getInd()};  // arr2 also contains only one element 
	        if (Arrays.deepEquals(arr1, arr2)) 
	            return true;
	        else
	            return false;
		}
		
		return false;
	}
	
	public Vector<Integer> getInd(){
		return this.ind;
	}
	
	public void addToInd(int newValue) {
		this.ind.add(newValue);
	}

	@Override
	public void evaluate() {
		this.mFittest = (this.ind.get(0) * this.ind.get(0)) - (this.ind.get(1) * this.ind.get(1));
	}

	@Override
	public int getFittest() {
		return this.mFittest;
	}

	@Override
	public Individu croisement(Individu otherParent) {
		TestIndividu newInd = new TestIndividu();
		newInd.addToInd(Math.max(this.getInd().get(0), ((TestIndividu) otherParent).getInd().get(0)));
		newInd.addToInd(Math.min(this.getInd().get(1), ((TestIndividu) otherParent).getInd().get(1)));
		
		return newInd;
	}

	@Override
	public void mutation() {
		int temp = this.getInd().get(0);
		this.getInd().set(0, this.getInd().get(1));
		this.getInd().set(1, temp);
	}
	
	@Override
	public String toString() {
		return "Value="+this.ind.get(0)+"|"+this.ind.get(1);
	}

}
