package com.export.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.export.be.model.Recipient;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Integer> {

}
