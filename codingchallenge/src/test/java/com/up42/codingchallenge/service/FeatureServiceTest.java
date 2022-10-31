package com.up42.codingchallenge.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.Matchers.equalTo;
import com.up42.codingchallenge.config.DataSource;
import com.up42.codingchallenge.dto.Feature;
import com.up42.codingchallenge.dto.FeatureResponse;
import com.up42.codingchallenge.dto.converter.FeatureResponseConverter;

import static org.hamcrest.MatcherAssert.assertThat;

//Test Class for FeatureService
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FeatureServiceTest {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private Map<UUID, Feature> featureMapping;
	@Autowired
	private FeatureResponseConverter featureResponseConverter;
	private FeatureService service;

	@BeforeEach
	void setUp() {
		service = new FeatureService(featureMapping, featureResponseConverter);
	}

	@Test
	void testGetFeatures() {
		List<FeatureResponse> actual = service.findAllFeatures();
		List<FeatureResponse> expected = dataSource.listAllFeatures().stream().map(featureResponseConverter::convert)
				.collect(Collectors.toList());
		// the order of items cannot be ensured
		assertThat(actual, equalTo(expected));
	}

	// test of findQuicklookByFeatureId method
	@Test
	void testGetFeatureQuickLook() {
		UUID uuid = UUID.fromString("39c2f29e-c0f8-4a39-a98b-deed547d6aea");
		byte[] actual = service.findQuicklookByFeatureId(uuid);
		byte[] expected = dataSource.getFeatureById(uuid).map(f -> f.getProperties().getQuicklook()).get();
		assertThat(actual, equalTo(expected));
	}
}
