package com.up42.codingchallenge.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeatureResponse {
	@NotNull
	private UUID id;
	private Long timestamp;
	private Long beginViewingDate;
	private Long endViewingDate;
	private String missionName;

}
