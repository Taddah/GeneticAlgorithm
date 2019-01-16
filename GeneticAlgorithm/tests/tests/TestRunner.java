package tests;

import java.util.logging.Level;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import debug.DebugLogger;

public class TestRunner {

	public static void main(String[] args) {	

		Result result = JUnitCore.runClasses(
				CritereDureeTest.class, 
				PopulationGenerationTest.class,
				CritereNombreIterationTest.class,
				AlgorithmeGenetiqueTest.class);	
		
		for (Failure failure : result.getFailures()) {		
			DebugLogger.getInstance().printLog(Level.INFO, failure.toString());
		}

		DebugLogger.getInstance().printLog(Level.INFO, "Result=="+result.wasSuccessful());
	}		
}
