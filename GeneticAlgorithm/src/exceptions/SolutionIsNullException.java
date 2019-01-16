package exceptions;

/**
 * Classe d'exception lev�e quand la solution renvoy� par l'algorithme est null
 */
public class SolutionIsNullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public SolutionIsNullException(String message) {
        super(message);
    }

}
