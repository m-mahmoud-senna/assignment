package com.ingenico.assignment.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Holds the request data 
 * transfer
 * 
 * @author Mohamed Mahmoud (m.mahmoud.senna@gmail.com)
 * @see com.ingenico.assignment.controller.BankingController.transfer(TransferRequest)
 *
 */
public class Transfer implements Serializable {

	private static final long serialVersionUID = 5061375405180356986L;
	private String sourceAccountNumber;
	private String destinationAccountNumber;
	private BigDecimal amount;

	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(String sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}

	public String getDestinationAccountNumber() {
		return destinationAccountNumber;
	}

	public void setDestinationAccountNumber(String destinationAccountNumber) {
		this.destinationAccountNumber = destinationAccountNumber;
	}

	public BigDecimal getAmount() {
		return amount.setScale(2);
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
