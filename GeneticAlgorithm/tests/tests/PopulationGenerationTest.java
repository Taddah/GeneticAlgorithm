package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import modeles.IIndividu;
import modeles.Population;
import user_classes.TestIndividu;

public class PopulationGenerationTest {

	private Population population; 
	
	public PopulationGenerationTest() {
		IIndividu ind = new TestIndividu();
		population = new Population(30, ind);
	}
	
	@Test
	public void isPopulationDiversified() {
		boolean populationDiversified = population.isPopulationDiversified();
		assertEquals(true, populationDiversified, "Population is not diversified !");
	}
}
