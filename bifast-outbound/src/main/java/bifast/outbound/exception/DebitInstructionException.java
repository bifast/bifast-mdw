package bifast.outbound.exception;

public class DebitInstructionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public DebitInstructionException(String errorMessage, Throwable err) {  
    	super(errorMessage, err);  
    }  

}
