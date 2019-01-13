package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import algorithmeGenetique.AlgorithmeGenetique;
import algorithmeGenetique.MultithradingAlgorithmeGenetique;
import critereArret.ArretIteration;
import critereArret.ICritereArret;
import modeles.Individu;
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
		Individu ind = new TestIndividu();
		ISelectionParents selectionParent = new SelectionParentsMeilleurs(30);
		IRemplacementIndividu remplacementIndividu = new RemplacementMoindreIndividu();
		ICritereArret critereArret = new ArretIteration(3);

		AlgorithmeGenetique ag = new AlgorithmeGenetique(50, ind, selectionParent, remplacementIndividu, critereArret);

		MultithradingAlgorithmeGenetique mag = new MultithradingAlgorithmeGenetique(3, ag);
		Resultat res = mag.startThreads();

		assertEquals(true, res.getNombreIteration() == 3 , "Crit�re d'arr�t sur le nombre d'it�ration non fonctionnel");
	}

}