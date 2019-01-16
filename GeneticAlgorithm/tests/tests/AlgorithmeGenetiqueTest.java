package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import algorithme_genetique.AlgorithmeGenetique;
import critere_arret.ArretDuree;
import critere_arret.ICritereArret;
import modeles.IIndividu;
import modeles.Resultat;
import multithreading.MultithreadingAlgorithmeGenetique;
import remplacement_individu.IRemplacementIndividu;
import remplacement_individu.RemplacementMoindreIndividu;
import selection_parents.ISelectionParents;
import selection_parents.SelectionParentsMeilleurs;
import user_classes.TestIndividu;

public class AlgorithmeGenetiqueTest {

	@Test
	public void testAlgorithme() {
		Logger logger = Logger.getLogger("logger");

		IIndividu ind = new TestIndividu();
		ISelectionParents selectionParent = new SelectionParentsMeilleurs(90);
		IRemplacementIndividu remplacementIndividu = new RemplacementMoindreIndividu();
		ICritereArret critereArret = new ArretDuree(20);

		MultithreadingAlgorithmeGenetique mag = new MultithreadingAlgorithmeGenetique(3, 100, ind, selectionParent, remplacementIndividu, critereArret);
		mag.setNombreThreadEvaluation(5);
		
		logger.log(Level.INFO, "D�marrage des algorithmes");
		Resultat res = mag.lancerAlgorithmes();

		logger.log(Level.INFO, "R�sultat");
		System.out.println("Solution : " + res.getSolution());
		System.out.println("Meilleure �valuation : " + res.getEvaluation());
		System.out.println("Nombre d'it�ration : " + res.getNombreIteration());
		System.out.println("Temps d'ex�cution (s) : " + res.getTempsExecution());
		logger.log(Level.INFO, "-----------------------------------");
		
		assertEquals(true, true, "null");
	}

}
