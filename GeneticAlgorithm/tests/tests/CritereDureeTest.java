package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import algorithmeGenetique.AlgorithmeGenetique;
import algorithmeGenetique.MultithradingAlgorithmeGenetique;
import critereArret.ArretDuree;
import critereArret.ICritereArret;
import modeles.IIndividu;
import modeles.Resultat;
import remplacementIndividu.IRemplacementIndividu;
import remplacementIndividu.RemplacementMoindreIndividu;
import selectionParents.ISelectionParents;
import selectionParents.SelectionParentsMeilleurs;
import userClasses.TestIndividu;

public class CritereDureeTest {

	public CritereDureeTest() {

	}

	@Test
	public void testAlgorithmeArretSeconde() {
		IIndividu ind = new TestIndividu();
		ISelectionParents selectionParent = new SelectionParentsMeilleurs(30);
		IRemplacementIndividu remplacementIndividu = new RemplacementMoindreIndividu();
		ICritereArret critereArret = new ArretDuree(3);

		AlgorithmeGenetique ag = new AlgorithmeGenetique(50, ind, selectionParent, remplacementIndividu, critereArret);

		MultithradingAlgorithmeGenetique mag = new MultithradingAlgorithmeGenetique(3, ag);
		Resultat res = mag.startThreads();

		assertEquals(true, res.getTempsExecution() > 3 && res.getTempsExecution() < 3.1, "Critère d'arrêt sur la durée non fonctionnel");
	}

}
