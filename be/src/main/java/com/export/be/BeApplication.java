package com.export.be;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.money.convert.ConversionQueryBuilder;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.javamoney.moneta.convert.ecb.ECBHistoricRateProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



//@SpringBootApplication
public class BeApplication {

	public static void main(String[] args) throws IOException {
		//SpringApplication.run(BeApplication.class, args);
		
		HttpClientBuilder builder = HttpClientBuilder.create();
		 
        try (CloseableHttpClient httpclient = builder.build())
        {
            HttpGet httpGet = new HttpGet("https://v6.exchangerate-api.com/v6/09911d8a51c1361d140897a0/latest/USD/");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpGet, responseHandler);
            Map<String, Object> jsonFileAsMap = new ObjectMapper().readValue(responseBody, new TypeReference<Map<String, Object>>() {});

            @SuppressWarnings("unchecked")
			Map<String, Object> ratesMap = (Map<String, Object>) jsonFileAsMap.get("conversion_rates");
              System.out.println(Double.parseDouble(String.valueOf(ratesMap.get("USD")) ));
		//System.out.println(ratesMap.toString());
			
			}
	
	}
}
	


