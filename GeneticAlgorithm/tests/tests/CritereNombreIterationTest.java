package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import algorithmeGenetique.AlgorithmeGenetique;
import algorithmeGenetique.MultithreadingAlgorithmeGenetique;
import critereArret.ArretIteration;
import critereArret.ICritereArret;
import modeles.IIndividu;
import modeles.Resultat;
import remplacementIndividu.IRemplacementIndividu;
import remplacementIndividu.RemplacementMoindreIndividu;
import selectionParents.ISelectionParents;
import selectionParents.SelectionParentsMeilleurs;
import userClasses.TestIndividu;

public class CritereNombreIterationTest {

	public CritereNombreIterationTest() {

	}

	@Test
	public void testAlgorithmeArretSeconde() {
		IIndividu ind = new TestIndividu();
		ISelectionParents selectionParent = new SelectionParentsMeilleurs(30);
		IRemplacementIndividu remplacementIndividu = new RemplacementMoindreIndividu();
		ICritereArret critereArret = new ArretIteration(3);

		AlgorithmeGenetique ag = new AlgorithmeGenetique(50, ind, selectionParent, remplacementIndividu, critereArret);

		MultithreadingAlgorithmeGenetique mag = new MultithreadingAlgorithmeGenetique(3, ag);
		Resultat res = mag.startThreads();

		assertEquals(true, res.getNombreIteration() == 3 , "Critère d'arrêt sur le nombre d'itération non fonctionnel");
	}

}
