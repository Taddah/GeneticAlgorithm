package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import critereArret.ArretDuree;
import critereArret.ICritereArret;
import modeles.Individu;
import remplacementIndividu.IRemplacementIndividu;
import remplacementIndividu.RemplacementMoindreIndividu;
import selectionParents.ISelectionParents;
import selectionParents.SelectionParentsMeilleurs;
import userClasses.TestIndividu;

/**
 * Main--- Exécution de la librairie
 * 
 * @author Thomas
 * @since 03/12/2018
 *
 */
public class Main {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger("logger");
		
		logger.log(Level.INFO, "Lancement du main");
		
		Individu ind = new TestIndividu();
		ISelectionParents selectionParent = new SelectionParentsMeilleurs(4999);
		IRemplacementIndividu remplacementIndividu = new RemplacementMoindreIndividu();
		ICritereArret critereArret = new ArretDuree(10);
		
		AlgorithmeGenetique ag = new AlgorithmeGenetique(5000, ind, selectionParent, remplacementIndividu, critereArret);
		//ag.setProbabiliteMutation(30);
		ag.startAlgorithme();
		
	}
}


