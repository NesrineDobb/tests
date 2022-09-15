package com.webatrio.techtest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webatrio.techtest.model.Personne;
import com.webatrio.techtest.repository.PersonRepository;

@RestController
public class PersonController {
	
	private PersonRepository personRepository;

	@Autowired
	public PersonController(PersonRepository personRepository) {

		this.personRepository = personRepository;
	}
	
	@PostMapping("/personne")
	public ResponseEntity<?> createPerson(@Valid @RequestBody Personne person) {
		
		Personne per = personRepository.save(person);
		return new ResponseEntity<>(per, HttpStatus.CREATED);
			

	}
	
	@GetMapping("/personnes")
	public List<Personne> getPersons(){
		
		
		return personRepository.findAllByOrderByIdAsc();
	}

}
