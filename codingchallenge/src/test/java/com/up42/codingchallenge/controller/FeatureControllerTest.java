package com.up42.codingchallenge.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.up42.codingchallenge.dto.FeatureResponse;
import com.up42.codingchallenge.dto.converter.FeatureResponseConverter;
import com.up42.codingchallenge.service.FeatureService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FeatureControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private FeatureService featureService;
	@Autowired
	private FeatureResponseConverter featureResponseConverter;

	// Test for the "/features" endpoint
	@Test
	void getFeaturesTest() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/features").accept("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		FeatureResponse[] features = mapper.readValue(result.getResponse().getContentAsString(),
				FeatureResponse[].class);
		assertThat(features.length, is(14));
		// the order of items cannot be ensured
		List<FeatureResponse> actual = Arrays.asList(features);
		List<FeatureResponse> expected = featureService.findAllFeatures();
		assertThat(actual.stream().filter(f -> !expected.contains(f)).findAny().isPresent(), is(false));
		assertThat(expected.stream().filter(f -> !actual.contains(f)).findAny().isPresent(), is(false));
	}

	// Test for the "/features/{id}" endpoint.
	@Test
	void getFeatureTest() throws Exception {
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.get("/features/39c2f29e-c0f8-4a39-a98b-deed547d6aea").accept("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		FeatureResponse feature = mapper.readValue(result.getResponse().getContentAsString(), FeatureResponse.class);
		assertThat(feature, notNullValue());

		assertThat(feature.getBeginViewingDate(), equalTo(1554831167697L));
		assertThat(feature.getEndViewingDate(), is(1554831202043L));
		assertThat(feature.getTimestamp(), is(1554831167697L));
		assertThat(feature.getMissionName(), is("Sentinel-1B"));

	}

	// Failure test for the "/features/{id}" endpoint which checks for a HTTP 404
	// status response
	@Test
	void getFeature404Test() throws Exception {
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.get("/features/3456-c0f8-4a39-a98b-thyr47d6apoa").accept("application/json"))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}

	// Test for the "/features/{id}/quicklook" endpoint.
	@Test
	void getQuickLookTest() throws Exception {
		String uuid = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get(String.format("/features/%s/quicklook", uuid)).accept("image/png"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		byte[] actual = result.getResponse().getContentAsByteArray();
		byte[] expected = featureService.findQuicklookByFeatureId(UUID.fromString(uuid));

		assertThat(actual, notNullValue());
		assertThat(actual.length, is(expected.length));
		assertThat(actual, equalTo(expected));
	}

	// Failure test for the "/features/{id}/quicklook" endpoint which checks for a
	// HTTP 404 status response
	@Test
	void testGetQuickLook404() throws Exception {
		String uuid = "3456-c0f8-4a39-a98b-thyr47d6apoa";
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.get(String.format("/features/%s/quicklook", uuid), 42L).accept("image/png"))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}

}
