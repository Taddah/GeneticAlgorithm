package debug;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe utilisé pour le débogage, design pattern singleton
 *
 */
public class DebugLogger {
	
	private Logger logger = null;

	//Instance du debugLoggr
    private static DebugLogger instance = null; 
  
  
    private DebugLogger() 
    { 
    	logger =  Logger.getLogger(DebugLogger.class.getName());
    } 
  
    public static DebugLogger getInstance() 
    { 
        if (instance == null) 
        	instance = new DebugLogger(); 
  
        return instance; 
    } 
    
    public void printLog(Level level, String erreur) {
    	this.logger.log(level, erreur);
    }
}
