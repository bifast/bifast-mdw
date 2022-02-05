package bifast.outbound.exception;

import bifast.outbound.pojo.FaultPojo;

public class DebitException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FaultPojo fault;
	
	public DebitException(String errorMessage) {  
    	super(errorMessage);  
    }

	public DebitException(String errorMessage, FaultPojo fault) {  
    	super(errorMessage);
    	this.fault = fault;
    }

	public FaultPojo getFault() {
		return fault;
	}


}
