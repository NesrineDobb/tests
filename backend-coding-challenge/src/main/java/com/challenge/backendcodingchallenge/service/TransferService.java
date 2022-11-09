package com.challenge.backendcodingchallenge.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.backendcodingchallenge.exception.NotEnoughFundsException;
import com.challenge.backendcodingchallenge.model.Account;
import com.challenge.backendcodingchallenge.model.Transfer;
import com.challenge.backendcodingchallenge.model.convert.CurrencyConverter;
import com.challenge.backendcodingchallenge.repository.TransferRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TransferService {

	private final TransferRepository transferRepository;
	private final AccountService accountService;
	//private final CurrencyConverter currencyConverter;

	@Autowired
	public TransferService(TransferRepository transferRepository, AccountService accountService) {
		this.transferRepository = transferRepository;
		this.accountService = accountService;
		
	}

	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public Transfer doTransfer(Transfer transfer) throws IOException {
		log.debug("transfer processing ...");
		Account accountFrom = accountService.findById(transfer.getFrom().getId());
		if (accountFrom.getBalance().compareTo(transfer.getAmount()) < 0) {
			throw new NotEnoughFundsException(accountFrom.getId());
		}

		Account accountTo = accountService.findById(transfer.getTo().getId());
		if (accountFrom.getCurrency() == null || accountTo.getCurrency() == null)
			throw new IllegalArgumentException("Convert currency takes 2 arguments!");
		transfer.setRate(CurrencyConverter.getRate(accountFrom.getCurrency(), accountTo.getCurrency()));
		BigDecimal moneyToTransfer = transfer.getAmount().multiply(BigDecimal.valueOf(transfer.getRate()));
		log.debug("the amount " + moneyToTransfer + " transferred from " + accountFrom.getCurrency() + " to "
				+ accountTo.getCurrency() + " with rate= " + transfer.getRate());
		accountFrom.setBalance(accountFrom.getBalance().subtract(transfer.getAmount()));
		accountTo.setBalance(accountTo.getBalance().add(moneyToTransfer));
		accountService.save(accountFrom);
		accountService.save(accountTo);
		transfer.setFrom(accountFrom);
		transfer.setTo(accountTo);
		return transferRepository.save(transfer);
	}

}
