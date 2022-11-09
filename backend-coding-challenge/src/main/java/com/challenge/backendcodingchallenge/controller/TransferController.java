package com.challenge.backendcodingchallenge.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.backendcodingchallenge.model.Transfer;
import com.challenge.backendcodingchallenge.service.TransferService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class TransferController {

	private TransferService transferService;

	@Autowired
	public TransferController(TransferService transferService) {
		this.transferService = transferService;
	}

	@ApiOperation(value = "Process the transfer")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "transfer done successfully", response = String.class) })
	@PostMapping(value = "/transfer")
	ResponseEntity<?> newTransfer(@RequestBody Transfer transfer) throws IOException {
		Transfer newTransfer = transferService.doTransfer(transfer);
		return ResponseEntity.ok(newTransfer);
	}
}
