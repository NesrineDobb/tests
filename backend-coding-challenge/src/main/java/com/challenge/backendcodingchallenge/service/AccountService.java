package com.challenge.backendcodingchallenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.backendcodingchallenge.exception.EntityNotFoundException;
import com.challenge.backendcodingchallenge.model.Account;
import com.challenge.backendcodingchallenge.repository.AccountRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AccountService {

	private final AccountRepository accountRepository;

	@Autowired
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Account save(Account account) {
		log.debug("creating a new account");
		return accountRepository.save(account);
	}

	public List<Account> findAll() {
		log.debug("Returning all accounts");
		return accountRepository.findAll();
	}

	public Account findById(int accountId) {
		log.debug("Returning account by id");
		return accountRepository.findById(accountId)
				.orElseThrow(() -> new EntityNotFoundException(Account.class, accountId));
	}

	public Account editById(int accountId, Account accountData) { 
		Account editedAccount = this.findById(accountId);
		if (accountData.getBalance() != null)
			editedAccount.setBalance(accountData.getBalance());
		if (accountData.getCurrency() != null)
			editedAccount.setCurrency(accountData.getCurrency());
		return accountRepository.save(editedAccount);

	}
}
