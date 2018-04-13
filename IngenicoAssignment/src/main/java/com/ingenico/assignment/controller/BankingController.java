package com.ingenico.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ingenico.assignment.model.Account;
import com.ingenico.assignment.model.Transfer;
import com.ingenico.assignment.service.TransferService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * API Gateway
 * @author <b>Mohamed Mahmoud</b> (m.mahmoud.senna@gmail.com)
 *
 */
@Api(value = "ingenico-assignment", produces ="application/json", description = "API gateway for performing banking operations")
@RestController
@RequestMapping("/")
public class BankingController {
	

	@Autowired(required = true)
	private TransferService transferService;

	@ApiOperation(value = "Getting all accounts", response = List.class)
	@GetMapping("/account")
	public List<Account> getAccounts() {
		return transferService.getAccounts();
	}

	@ApiOperation(value = "Create a new account", response = Account.class)
	@PostMapping("/account")
	public Account createAccount(@RequestBody Account account) {
		return transferService.createAccount(account);
	}

	@ApiOperation(value = "Get account by account number", response = Account.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved account by number"), @ApiResponse(code = 404, message = "If account not found") })
	@GetMapping("/account/{accountNumber}")
	public Account account(@PathVariable String accountNumber) {
		return transferService.getAccount(accountNumber);
	}

	@ApiOperation(value = "Transfer the specified amount form source account to destination account")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Transfer completed successfullt"), @ApiResponse(code = 404, message = "source account doesn't have sufficient funds") })
	@PutMapping("/transfer")
	@ResponseBody
	public void transfer(@RequestBody Transfer transferRequest) {
		transferService.transfer(transferRequest);
	}
}
