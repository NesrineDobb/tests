package com.challenge.backendcodingchallenge.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge.backendcodingchallenge.model.Account;
import com.challenge.backendcodingchallenge.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AccountService accountService;

	private static Account account;

	@BeforeAll
	static void initializeData() {
		account = new Account();
	}

	private void setAccount(int id, String currency, BigDecimal balance) {
		account.setId(id);
		account.setCurrency(currency);
		account.setBalance(balance);
	}

	@Test
	public void allAccounts_whenValidMethodAndUrlAndPathVariable_thenReturns200() throws Exception {
		// given
		// ...
		// when
		mockMvc.perform(get("/accounts"))
				// then
				.andExpect(status().isOk());
	}

	@Test
	public void oneAccount_whenValidMethodAndUrlAndPathVariable_thenReturns200() throws Exception {
		// given
		Long accountId = 1L;
		// when
		mockMvc.perform(get("/accounts/{accountId}", accountId))
				// then
				.andExpect(status().isOk());
	}

	@Test
	public void newAccount_whenValidMethodAndUrlAndRequestBody_thenReturns201() throws Exception {
		// given
		int accountId = 1;
		setAccount(accountId, "EUR", BigDecimal.ONE);
		given(accountService.save(account)).willReturn(account);
		// when
		ResultActions resultActions = mockMvc.perform(
				post("/accounts").contentType("application/json").content(objectMapper.writeValueAsString(account)));
		// then
		resultActions.andExpect(status().isCreated());
	}

	@Test
	public void editAccount_whenValidMethodAndUrlAndRequestBody_thenReturns201() throws Exception {
		// given
		int accountId = 1;
		setAccount(accountId, "EUR", BigDecimal.ONE);
		given(accountService.editById(accountId, account)).willReturn(account);
		// when
		ResultActions resultActions = mockMvc.perform(put("/accounts/{accountId}", accountId)
				.contentType("application/json").content(objectMapper.writeValueAsString(account)));
		// then
		resultActions.andExpect(status().isCreated());
	}

	@Test
	public void editAccount_whenInvalidRequestBody_thenReturns400() throws Exception {
		// given
		int accountId = 1;
		// when
		ResultActions resultActions = mockMvc.perform(put("/accounts/{accountId}", accountId));
		// then
		resultActions.andExpect(status().isBadRequest());
	}

}
