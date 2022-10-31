package com.up42.codingchallenge.dto.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.up42.codingchallenge.dto.Feature;
import com.up42.codingchallenge.dto.FeatureResponse;

@Component
public class FeatureResponseConverter implements Converter<Feature, FeatureResponse> {

	@Override
	public FeatureResponse convert(Feature feature) {

		FeatureResponse result = FeatureResponse.builder().id(feature.getProperties().getId())
				.timestamp(feature.getProperties().getTimestamp())
				.beginViewingDate(feature.getProperties().getAcquisition().getBeginViewingDate())
				.endViewingDate(feature.getProperties().getAcquisition().getEndViewingDate())
				.missionName(feature.getProperties().getAcquisition().getMissionName()).build();

		return result;
	}

}
