package com.challenge.backendcodingchallenge.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.backendcodingchallenge.exception.EntityNotFoundException;
import com.challenge.backendcodingchallenge.model.Account;
import com.challenge.backendcodingchallenge.repository.AccountRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	private AccountService accountService;

	@Mock
	private AccountRepository accountRepository;

	private Account account;

	@BeforeEach
	public void initializeData() {
		accountService = new AccountService(accountRepository);
		account = new Account();
	}

	private void setAccount(int id, String currency, BigDecimal balance) {

		account.setId(id);
		account.setCurrency(currency);
		account.setBalance(balance);

	}

	@Test
	public void findById_whenExists_returnsTheAccount() {
		// given
		int accountId = 1;
		setAccount(accountId, "EUR", BigDecimal.ONE);
		given(accountRepository.findById(accountId)).willReturn(Optional.of(account));
		// when
		Account actualAccount = accountService.findById(accountId);
		// then
		assertThat(actualAccount).isEqualTo(account);
	}

	@Test
	public void findById_whenNotExists_throwsEntityNotFoundException() {
		// given
		int accountId = 1;
		given(accountRepository.findById(any(Integer.class))).willReturn(Optional.empty());
		// when
		try {
			accountService.findById(accountId);
		}
		// then
		catch (EntityNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Account was not found for parameter %s", accountId);
		}
	}

	@Test
	public void save_whenValidInput_returnsTheAccount() {
		// given
		int accountId = 1;
		setAccount(accountId, "USD", BigDecimal.ZERO);
		given(accountRepository.save(account)).willReturn(account);
		// when
		Account actualAccount = accountService.save(account);
		// then
		assertThat(actualAccount).isEqualTo(account);
	}

	@Test
	public void editById_editTheAccountAndReturnsIt() {
		// given
		int accountId = 1;
		setAccount(accountId, "USD", BigDecimal.ZERO);
		Account accountData = new Account();
		accountData.setCurrency("EUR");
		accountData.setBalance(BigDecimal.ONE);
		given(accountRepository.findById(accountId)).willReturn(Optional.of(account));
		given(accountRepository.save(account)).willReturn(account);
		// when
		Account actualAccount = accountService.editById(accountId, accountData);
		// then
		assertThat(actualAccount.getCurrency()).isEqualTo(accountData.getCurrency());
		assertThat(actualAccount.getBalance()).isEqualTo(accountData.getBalance());
		assertThat(actualAccount.getId()).isEqualTo(accountId);
	}

}
