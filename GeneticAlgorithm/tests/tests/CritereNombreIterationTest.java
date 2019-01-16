package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import algorithme_genetique.AlgorithmeGenetique;
import critere_arret.ArretIteration;
import critere_arret.ICritereArret;
import modeles.IIndividu;
import modeles.Resultat;
import multithreading.MultithreadingAlgorithmeGenetique;
import remplacement_individu.IRemplacementIndividu;
import remplacement_individu.RemplacementMoindreIndividu;
import selection_parents.ISelectionParents;
import selection_parents.SelectionParentsMeilleurs;
import user_classes.TestIndividu;

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
		Resultat res = mag.lancerAlgorithmes();

		assertEquals(true, res.getNombreIteration() == 3 , "Critère d'arrêt sur le nombre d'itération non fonctionnel");
	}

}
