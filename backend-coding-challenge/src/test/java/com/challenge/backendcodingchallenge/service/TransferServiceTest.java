package com.challenge.backendcodingchallenge.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.backendcodingchallenge.exception.NotEnoughFundsException;
import com.challenge.backendcodingchallenge.model.Account;
import com.challenge.backendcodingchallenge.model.Transfer;
import com.challenge.backendcodingchallenge.repository.TransferRepository;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

	private TransferService transferService;

	@Mock
	private TransferRepository transferRepository;

	@Mock
	private AccountService accountService;

	private static Account from;
	private static Account to;
	private static Transfer transfer;

	@BeforeAll
	static void initializeData() {
		from = new Account();
		from.setId(1);
		from.setCurrency("USD");
		to = new Account();
		to.setId(2);
		to.setCurrency("USD");
		transfer = new Transfer();
		transfer.setId(1);
		transfer.setFrom(from);
		transfer.setTo(to);
		transfer.setAmount(BigDecimal.ONE);

	}

	@BeforeEach
	void initTransferServer() {
		transferService = new TransferService(transferRepository, accountService);

	}

	@Test
	void doTransfer_whenItIsNotEnoughFunds_abortTheTransfer() throws IOException {
		// given
		from.setBalance(BigDecimal.ZERO);
		to.setBalance(BigDecimal.ONE);
		when(accountService.findById(from.getId())).thenReturn(from);
		// when
		try {
			transferService.doTransfer(transfer);
		}
		// then
		catch (NotEnoughFundsException e) {
			assertThat(from.getBalance()).isEqualTo(BigDecimal.ZERO);
			assertThat(to.getBalance()).isEqualTo(BigDecimal.ONE);
			assertThat(e.getMessage())
					.isEqualTo("Not enough funds in account with id= 1. The transfer has been aborted.");
		}
	}

	@Test
	void doTransfer_whenItIsEnoughFunds_doTheTransfer() throws IOException {
		// given
		from.setBalance(BigDecimal.ONE);
		to.setBalance(BigDecimal.ONE);
		when(accountService.findById(from.getId())).thenReturn(from);
		when(accountService.findById(to.getId())).thenReturn(to);
		when(accountService.save(any(Account.class))).then(returnsFirstArg());
		when(transferRepository.save(any(Transfer.class))).then(returnsFirstArg());
		// when
		transferService.doTransfer(transfer);
		// then
		assertThat(from.getBalance()).isEqualTo(BigDecimal.ZERO);
		assertThat(to.getBalance()).isEqualTo(BigDecimal.valueOf(2.0));
	}
}
