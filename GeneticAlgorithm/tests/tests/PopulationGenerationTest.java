package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modeles.Individu;
import modeles.Population;
import userClasses.TestIndividu;

public class PopulationGenerationTest {

	private Population population; 
	
	public PopulationGenerationTest() {
		Individu ind = new TestIndividu();
		population = new Population(30, ind);
	}
	
	@Test
	public void isPopulationDiversified() {
		boolean populationDiversified = population.isPopulationDiversified();
		assertEquals(true, populationDiversified, "Population is not diversified !");
	}
}
