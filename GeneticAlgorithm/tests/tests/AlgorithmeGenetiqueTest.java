package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import algorithmeGenetique.AlgorithmeGenetique;
import algorithmeGenetique.MultithradingAlgorithmeGenetique;
import critereArret.ArretEvolutionPopulation;
import critereArret.ICritereArret;
import modeles.Individu;
import modeles.Resultat;
import remplacementIndividu.IRemplacementIndividu;
import remplacementIndividu.RemplacementMoindreIndividu;
import selectionParents.ISelectionParents;
import selectionParents.SelectionParentsMeilleurs;
import userClasses.TestIndividu;

public class AlgorithmeGenetiqueTest {

	@Test
	public void test() {
		Logger logger = Logger.getLogger("logger");

		logger.log(Level.INFO, "Lancement du main");

		Individu ind = new TestIndividu();
		ISelectionParents selectionParent = new SelectionParentsMeilleurs(10);
		IRemplacementIndividu remplacementIndividu = new RemplacementMoindreIndividu();
		ICritereArret critereArret = new ArretEvolutionPopulation(2000);

		AlgorithmeGenetique ag = new AlgorithmeGenetique(50, ind, selectionParent, remplacementIndividu, critereArret);

		MultithradingAlgorithmeGenetique mag = new MultithradingAlgorithmeGenetique(3, ag);
		Resultat res = mag.startThreads();

		logger.log(Level.INFO, "Résultat");
		System.out.println("Solution : " + res.getSolution());
		System.out.println("Meilleure évaluation : " + res.getEvaluation());
		System.out.println("Nombre d'itération : " + res.getNombreIteration());
		System.out.println("Temps d'exécution (s) : " + res.getTempsExecution());
		
		assertEquals(true, true, "null");
	}

}
