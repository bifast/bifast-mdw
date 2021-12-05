package bifast.outbound.exception;

import bifast.outbound.pojo.flat.FlatAdmi002Pojo;

public class Admi002Exception extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static FlatAdmi002Pojo admi002;
	
	public Admi002Exception(String errorMessage) {  
    	super(errorMessage);  
    }

	public Admi002Exception(String errorMessage, FlatAdmi002Pojo admi002) {  
    	super(errorMessage);
    	Admi002Exception.admi002 = admi002;
    }

	public static FlatAdmi002Pojo getAdmi002() {
		return admi002;
	}


}
