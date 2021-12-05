package bifast.outbound.exception;

public class TrnsLimitException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public TrnsLimitException(String errorMessage) {  
    	super(errorMessage);  
    }


}
