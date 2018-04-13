package com.ingenico.assignment.service;

import java.math.BigDecimal;
import java.util.List;

import com.ingenico.assignment.model.Account;
import com.ingenico.assignment.model.Transfer;
import com.ingenico.assignment.service.exception.AccountNotFoundException;

/**
 * Transfer service used to create monetary accounts and transfer amounts from
 * account to account
 * 
 * @author <b>Mohamed Mahmoud</b> (m.mahmoud.senna@gmail.com)
 *
 */
public interface TransferService {

	/**
	 * 
	 * @param account
	 * 
	 * @return Newly created account
	 */
	Account createAccount(Account account);

	/**
	 * Returns all accounts
	 * 
	 * @return list of accounts
	 */
	List<Account> getAccounts();

	/**
	 * Returns the account for the specified account number
	 * 
	 * @param accountNumber
	 * @return Account if exists
	 * @throws AccountNotFoundException
	 *             if account doesn't exist
	 */
	Account getAccount(String accountNumber);

	/**
	 * Transfer amount for source account to destination account
	 * 
	 * @param transferRequest
	 * @return
	 */
	void transfer(Transfer transferRequest);

}