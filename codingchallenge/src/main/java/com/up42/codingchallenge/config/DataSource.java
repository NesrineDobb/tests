package com.up42.codingchallenge.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.up42.codingchallenge.dto.Feature;
import com.up42.codingchallenge.dto.FeatureCollection;
import com.up42.codingchallenge.exception.InternalServerErrorException;

@Configuration
public class DataSource {

	private static final String fileName = "source-data.json";
	private List<FeatureCollection> featureCollections;

	// Creating map of features as a spring bean.
	@Bean(name = "featuresMapping")
	public Map<UUID, Feature> load() {
		// create Object Mapper
		ObjectMapper mapper = new ObjectMapper();

		// read JSON file and map/convert to java POJO
		try {
			featureCollections = Arrays.asList(mapper
					.readValue(getClass().getClassLoader().getResourceAsStream(fileName), FeatureCollection[].class));
		} catch (IOException e) {
			e.printStackTrace();
			throw new InternalServerErrorException();
		}
		Map<UUID, Feature> featuresMapping = new HashMap<>();
		featureCollections.stream()
				.forEach(x -> x.getFeatures().stream().forEach(feature -> mapFeature(featuresMapping, feature)));
		return featuresMapping;
	}

	public List<Feature> listAllFeatures() {
		return featureCollections.stream().flatMap(x -> x.getFeatures().stream()).collect(Collectors.toList());
	}

	public Optional<Feature> getFeatureById(UUID id) {
		return listAllFeatures().stream().filter(f -> f.getProperties().getId().equals(id)).findAny();
	}

	// add a new feature in the map of features
	private void mapFeature(Map<UUID, Feature> map, Feature feature) {
		map.put(feature.getProperties().getId(), feature);
	}

}
