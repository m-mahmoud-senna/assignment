package com.ingenico.assignment.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingenico.assignment.model.Account;
import com.ingenico.assignment.model.Transfer;
import com.ingenico.assignment.repository.DataRepository;
import com.ingenico.assignment.service.exception.AccountNotFoundException;

/**
 * TransferService implementation class
 * 
 * @author <b>Mohamed Mahmoud</b> (m.mahmoud.senna@gmail.com)
 *
 */
@Service
public class TransferServiceImpl implements TransferService {
	@Autowired(required = true)
	private DataRepository repository;
	private final static Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);
	
	@Override
	public Account createAccount(Account account) {
		logger.debug("Creating account {}  with initial balance {}", account.getNumber(), account.getBalance());
		return repository.save(account);

	}

	@Override
	public List<Account> getAccounts() {
		logger.debug("Get All accounts started");
		return repository.findAll();

	}

	@Override
	public Account getAccount(String accountNumber) {
		logger.debug("Invoking getAccount with account number {}", accountNumber);

		Account account = repository.findById(accountNumber).orElseThrow(() -> new AccountNotFoundException(getAccountNotFoundMessage(accountNumber)));

		logger.debug("Found Account {} with balance {}", account.getNumber(), account.getBalance());
		return account;
	}

	@Transactional
	@Override
	public void transfer(Transfer transferRequest) {
		String sourceAccountNumber = transferRequest.getSourceAccountNumber();
		String destinationAccountNumber = transferRequest.getDestinationAccountNumber();
		BigDecimal amount = transferRequest.getAmount();
		logger.debug("Initiating transfer request from {} to {} amount {} ", sourceAccountNumber, destinationAccountNumber, amount);

		Account sourceAccount = repository.findByNumber(sourceAccountNumber)
				.orElseThrow(() -> new AccountNotFoundException(getAccountNotFoundMessage(sourceAccountNumber)));
		Account destinationAccount = repository.findByNumber(destinationAccountNumber)
				.orElseThrow(() -> new AccountNotFoundException(getAccountNotFoundMessage(destinationAccountNumber)));
		sourceAccount.withdraw(amount);
		destinationAccount.deposit(amount);
		logger.debug("Transfer request completed successfully");
		//In real world application, the method will return a transfer reference and save the transfer operation in transaction history 
	}

	private String getAccountNotFoundMessage(String accountNumber) {
		return "Couldn't find account [" + accountNumber + "]";
	}

}
