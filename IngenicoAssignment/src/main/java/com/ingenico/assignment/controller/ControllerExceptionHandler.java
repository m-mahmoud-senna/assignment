package com.ingenico.assignment.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.ingenico.assignment.controller.beans.ErrorResponse;
import com.ingenico.assignment.service.exception.AccountNotFoundException;
import com.ingenico.assignment.service.exception.InsufficientFundsException;

/**
 * Exception Handler to convert business/technical exceptions to correspondent
 * HTTP status codes
 * 
 * @author Mohamed Mahmoud (m.mahmoud.senna@gmail.com)
 */
@ControllerAdvice
@RestController
public class ControllerExceptionHandler {
	private final static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	/**
	 * Convert AccountNotFoundException to HTTP status code 404
	 * 
	 * @param AccountNotFoundException
	 * @param WebRequest
	 * @return ResponseEntity with status 404
	 */
	@ExceptionHandler(AccountNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException e, WebRequest request) {
		return populateError(e, HttpStatus.NOT_FOUND);
	}

	/**
	 * Convert InsufficientFundsException to HTTP status code 400
	 * 
	 * @param InsufficientFundsException
	 * @param WebRequest
	 * @return ResponseEntity with status 400
	 */
	@ExceptionHandler(InsufficientFundsException.class)
	public final ResponseEntity<ErrorResponse> handleInsufficientFundsException(InsufficientFundsException e, WebRequest request) {
		return populateError(e, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Convert All other exceptions to HTTP status code 500
	 * 
	 * @param Exception
	 * @param WebRequest
	 * @return ResponseEntity with status 500
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleGeneralException(Exception e, WebRequest request) {
	
		return populateError(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ErrorResponse> populateError(Exception e, HttpStatus status) {
		logger.warn("Error occured {}", e.getMessage());
		ErrorResponse errorDetails = new ErrorResponse(new Date(), e.getMessage());
		return new ResponseEntity<>(errorDetails, status);
	}
}
