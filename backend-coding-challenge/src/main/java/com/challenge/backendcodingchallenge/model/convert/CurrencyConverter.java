package com.challenge.backendcodingchallenge.model.convert;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import com.challenge.backendcodingchallenge.exception.UnknownCurrencyException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

//use the external api "ExchangeRate-Api" to retrieve  uptodate exchange rate
@Component
public class CurrencyConverter {
	

	private static final String API_KEY = "09911d8a51c1361d140897a0";

	public static double getRate(String from, String to) throws UnknownCurrencyException {
		HttpClientBuilder builder = HttpClientBuilder.create();

		try (CloseableHttpClient httpClient = builder.build()) {
			HttpGet httpGet = new HttpGet("https://v6.exchangerate-api.com/v6/09911d8a51c1361d140897a0/latest/" + from);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpClient.execute(httpGet, responseHandler);
			Map<String, Object> jsonFileAsMap = new ObjectMapper().readValue(responseBody,
					new TypeReference<Map<String, Object>>() {
					});

			@SuppressWarnings("unchecked")
			Map<String, Object> ratesMap = (Map<String, Object>) jsonFileAsMap.get("conversion_rates");
			return Double.parseDouble(String.valueOf(ratesMap.get(to)));
		} catch (IOException e) {
			throw new UnknownCurrencyException();
		}

	}
}