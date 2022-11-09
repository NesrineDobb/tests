package com.challenge.backendcodingchallenge.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.challenge.backendcodingchallenge.model.Account;
import com.challenge.backendcodingchallenge.model.Transfer;
import com.challenge.backendcodingchallenge.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransferController.class)
class TransferControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TransferService transferService;

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
		transfer.setAmount(BigDecimal.valueOf(100.00));
	}

	@BeforeEach
	public void reset() {
		from.setBalance(BigDecimal.valueOf(100.00));
		to.setBalance(BigDecimal.valueOf(50.00));
	}

	@Test
	void newTransfer_whenValidMethodAndUrlAndContentTypeAndRequestBody_thenReturns200() throws Exception {
		// given
		// ...
		// when
		mockMvc.perform(
				post("/transfer").contentType("application/json").content(objectMapper.writeValueAsString(transfer)))
				// then
				.andExpect(status().isOk());
	}

	@Test
	void newTransfer_whenValidInput_thenCallsDoTransfer() throws Exception {
		// given
		// ...
		// when
		mockMvc.perform(
				post("/transfer").contentType("application/json").content(objectMapper.writeValueAsString(transfer)));
		// then
		ArgumentCaptor<Transfer> transferArgumentCaptor = ArgumentCaptor.forClass(Transfer.class);
		verify(transferService, times(1)).doTransfer(transferArgumentCaptor.capture());
		assertThat(transferArgumentCaptor.getValue().getAmount()).isEqualTo(BigDecimal.valueOf(100.00));
		assertThat(transferArgumentCaptor.getValue().getId()).isEqualTo(1);
		assertThat(transferArgumentCaptor.getValue().getFrom().getId()).isEqualTo(1);
		assertThat(transferArgumentCaptor.getValue().getTo().getId()).isEqualTo(2);
	}

}
