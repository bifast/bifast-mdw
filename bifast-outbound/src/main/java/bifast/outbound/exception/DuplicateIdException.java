package bifast.outbound.exception;

public class DuplicateIdException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateIdException(String errorMessage) {  
		
    	super(errorMessage);  
    }  

}
