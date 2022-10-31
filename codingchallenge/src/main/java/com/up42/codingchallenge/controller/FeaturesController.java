package com.up42.codingchallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.up42.codingchallenge.dto.FeatureResponse;
import com.up42.codingchallenge.service.FeatureService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.util.List;
import java.util.UUID;

@RestController
public class FeaturesController {

	private FeatureService featureService;

	@Autowired
	public FeaturesController(FeatureService featureService) {
		this.featureService = featureService;
	}

	@ApiOperation(value = "Return all features")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Features returned successfully", response = String.class) })
	// GET method responsible to return a list of all features.
	@GetMapping(value = "/features", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FeatureResponse>> getFeatures() {
		return new ResponseEntity<>(featureService.findAllFeatures(), HttpStatus.OK);
	}

	@ApiOperation(value = "Return feature by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Feature returned successfully", response = FeatureResponse.class),
			@ApiResponse(code = 404, message = "Feature not found.") })
	// GET method responsible to return a single feature according to the {id} in
	// the URL path.
	@GetMapping(value = "/features/{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<FeatureResponse> getFeature(@PathVariable("id") final UUID featureId) {
		return ResponseEntity.ok().body(featureService.findFeatureById(featureId));
	}

	@ApiOperation(value = "Return quicklook image by feature id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Image returned successfully", response = byte[].class),
			@ApiResponse(code = 404, message = "Feature not found.") })
	// GET method responsible to return an image according to the {id} of the
	// respective feature in the URL path.
	@GetMapping(value = "/features/{id}/quicklook", produces = IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getQuickLookImage(@PathVariable("id") final UUID featureId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(featureService.findQuicklookByFeatureId(featureId),
				headers, HttpStatus.OK);
		return responseEntity;
	}
}
