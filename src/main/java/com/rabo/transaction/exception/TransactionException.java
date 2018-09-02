package com.rabo.transaction.exception;

/**
 * @author Nandini
 * 
 * Customized exception class to handle transaction processing issues.
 *
 */
public class TransactionException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -554356302279349794L;
	
	public TransactionException(String message) {
		super(message);
	}

}
