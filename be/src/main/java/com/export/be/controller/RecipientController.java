package com.export.be.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.export.be.exception.ResourceNotFoundException;
import com.export.be.model.Recipient;
import com.export.be.repository.RecipientRepository;

@RestController
public class RecipientController {

	@Autowired
	RecipientRepository recipientRepository;

	@GetMapping("/recipients")
	public Page<Recipient> getAllPosts(Pageable pageable) {
		return recipientRepository.findAll(pageable);
	}

	@PostMapping("/recipients")
	public Recipient createRecipient(@Valid @RequestBody Recipient recipient) {
		return recipientRepository.save(recipient);
	}

	@PutMapping("/recipients/{recipientId}")
	public Recipient updateRecipient(@PathVariable int recipientId, @Valid @RequestBody Recipient recipientRequest) {
		return recipientRepository.findById(recipientId).map(recipient -> {
			recipient.setNom(recipientRequest.getNom());
			recipient.setAdresse(recipientRequest.getAdresse());
			recipient.setCodePostal(recipientRequest.getCodePostal());
			recipient.setVille(recipientRequest.getVille());
			recipient.setPays(recipientRequest.getPays());
			return recipientRepository.save(recipient);
		}).orElseThrow(() -> new ResourceNotFoundException("RecipientId " + recipientId + " not found"));
	}

	@DeleteMapping("/recipients/{recipientId}")
	public ResponseEntity<?> deletePost(@PathVariable int recipientId) {
		return recipientRepository.findById(recipientId).map(recipient -> {
			recipientRepository.delete(recipient);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("RecipientId " + recipientId + " not found"));
	}

}
