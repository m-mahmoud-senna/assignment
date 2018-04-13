package com.ingenico.assignment.service.exception;

/**
 * AccountNotFoundException will be raised in case the account desired not found
 * 
 * @author <b>Mohamed Mahmoud</b> (m.mahmoud.senna@gmail.com)
 *
 */
public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7779752385843132097L;

	public AccountNotFoundException() {
		super();
	}

	public AccountNotFoundException(String message) {
		super(message);
	}
}
