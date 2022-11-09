package com.challenge.backendcodingchallenge.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.backendcodingchallenge.model.Account;
import com.challenge.backendcodingchallenge.service.AccountService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class AccountController {

	private AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@ApiOperation(value = "Return all accounts")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "accounts returned successfully", response = String.class) })
	@GetMapping(value = "/accounts", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Account>> allAccounts() {

		return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);

	}

	@ApiOperation(value = "Return account by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "account returned successfully", response = Account.class),
			@ApiResponse(code = 404, message = "account not found.") })
	@GetMapping(value = "/accounts/{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> getAccount(@PathVariable("id") final int accountId) {
		return ResponseEntity.ok().body(accountService.findById(accountId));
	}

	@ApiOperation(value = "Create new Account")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "accounts created successfully", response = String.class) })
	@PostMapping(value = "/accounts", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		return new ResponseEntity<>(accountService.save(account), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Edit account by id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "account saved successfully", response = Account.class),
			@ApiResponse(code = 404, message = "account not found.") })
	@PutMapping("/accounts/{accountId}")
	public ResponseEntity<Account> updateAccount(@PathVariable int accountId, @Valid @RequestBody Account account) {
		return new ResponseEntity<>(accountService.editById(accountId, account), HttpStatus.CREATED);

	}
}
