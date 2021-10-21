package bifast.outbound.exception;

public class InputValidatationException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InputValidatationException(String errorMessage) {  
		
    	super(errorMessage);  
    }  

}
