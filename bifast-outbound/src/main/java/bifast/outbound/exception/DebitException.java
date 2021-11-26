package bifast.outbound.exception;

import bifast.outbound.pojo.FaultPojo;

public class DebitException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static FaultPojo fault;
	
	public DebitException(String errorMessage) {  
    	super(errorMessage);  
    }

	public DebitException(String errorMessage, FaultPojo fault) {  
    	super(errorMessage);
    	DebitException.fault = fault;
    }

	public static FaultPojo getFault() {
		return fault;
	}


}
