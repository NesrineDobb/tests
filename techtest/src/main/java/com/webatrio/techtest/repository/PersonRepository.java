package com.webatrio.techtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.webatrio.techtest.model.Personne;

@Repository
public interface PersonRepository extends JpaRepository<Personne, Integer>{

	public List<Personne> findAllByOrderByIdAsc();
}
