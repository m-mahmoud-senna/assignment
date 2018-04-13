package com.ingenico.assignment.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.ingenico.assignment.service.exception.InsufficientFundsException;

/**
 * Account Model
 * 
 * @author <b>Mohamed Mahmoud</b> (m.mahmoud.senna@gmail.com)
 *
 */
@Entity
public class Account implements Serializable {

	private static final long serialVersionUID = -1608534183700984370L;
	@Id
	private String number;
	private BigDecimal balance;

	// Mandatory for JPA
	private Account() {
	}

	public Account(String number, BigDecimal balance) {
		this.number = number;
		this.balance = balance;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * Credit account with specified amount
	 * 
	 * @param amount
	 * @return new account balance after credit
	 */
	@Transient
	public BigDecimal deposit(BigDecimal amount) {
		balance = balance.add(amount);
		return balance;
	}

	/**
	 * Debit account with specified amount
	 * 
	 * @param amount
	 * @return new account balance after debit opseration
	 * @throws InsufficientFundsException if debit amount > balance
	 */
	@Transient
	public BigDecimal withdraw(BigDecimal amount) {
		if (amount.compareTo(balance) == 1) {
			throw new InsufficientFundsException("Can't transfer amount[" + amount + "], current balance is [" + balance + "]");
		}
		balance = balance.subtract(amount) ;
		return balance;
	}
}
