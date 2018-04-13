package com.ingenico.assignment.service.exception;

/**
 * InsufficientFundsException will be raised if there is an attempt to debit
 * amount bigger than account balance
 * 
 * @author <b>Mohamed Mahmoud</b> (m.mahmoud.senna@gmail.com)
 *
 */
public class InsufficientFundsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InsufficientFundsException() {
		super();
	}

	public InsufficientFundsException(String message) {
		super(message);
	}
}
