package com.up42.codingchallenge.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.up42.codingchallenge.dto.Feature;
import com.up42.codingchallenge.dto.FeatureResponse;
import com.up42.codingchallenge.dto.converter.FeatureResponseConverter;
import com.up42.codingchallenge.exception.FeatureNotfoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FeatureService {

	private Map<UUID, Feature> featureMapping;
	private FeatureResponseConverter featureResponseConverter;

	@Autowired
	public FeatureService(Map<UUID, Feature> featureMapping, FeatureResponseConverter featureResponseConverter) {
		this.featureMapping = featureMapping;
		this.featureResponseConverter = featureResponseConverter;
	}

	public byte[] findQuicklookByFeatureId(final UUID id) {
		log.debug("Returning quicklook for feature by id.");
		return getFeatureById(id).getProperties().getQuicklook();
	}

	// Return all features.
	public List<FeatureResponse> findAllFeatures() {
		log.debug("Returning all features");
		return featureMapping.values().stream().map(featureResponseConverter::convert).collect(Collectors.toList());
	}

	// Find a feature by id
	public FeatureResponse findFeatureById(final UUID id) {
		log.debug("Returning feature by id");
		return featureResponseConverter.convert(getFeatureById(id));
	}

	// Find a feature by id
	public Feature getFeatureById(final UUID id) {
		Optional<Feature> feature = Optional.ofNullable(featureMapping.get(id));
		return feature.map((obj) -> {
			return obj;
		}).orElseThrow(() -> new FeatureNotfoundException());
	}
}
