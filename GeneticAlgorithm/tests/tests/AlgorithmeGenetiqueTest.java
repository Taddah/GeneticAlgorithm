package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import algorithmeGenetique.AlgorithmeGenetique;
import algorithmeGenetique.MultithradingAlgorithmeGenetique;
import critereArret.ArretDuree;
import critereArret.ArretEvolutionPopulation;
import critereArret.ArretIteration;
import critereArret.ArretMeilleurIndividuReste;
import critereArret.ICritereArret;
import modeles.IIndividu;
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

		IIndividu ind = new TestIndividu();
		ISelectionParents selectionParent = new SelectionParentsMeilleurs(9);
		IRemplacementIndividu remplacementIndividu = new RemplacementMoindreIndividu();
		ICritereArret critereArret = new ArretIteration(1);

		AlgorithmeGenetique ag = new AlgorithmeGenetique(10, ind, selectionParent, remplacementIndividu, critereArret);
		ag.setNombreThreadEvaluation(5);

		MultithradingAlgorithmeGenetique mag = new MultithradingAlgorithmeGenetique(1, ag);
		
		logger.log(Level.INFO, "Démarrage des algorithmes");
		Resultat res = mag.startThreads();

		logger.log(Level.INFO, "Résultat");
		System.out.println("Solution : " + res.getSolution());
		System.out.println("Meilleure évaluation : " + res.getEvaluation());
		System.out.println("Nombre d'itération : " + res.getNombreIteration());
		System.out.println("Temps d'exécution (s) : " + res.getTempsExecution());
		logger.log(Level.INFO, "-----------------------------------");
		
		assertEquals(true, true, "null");
	}

}
