package com.challenge.backendcodingchallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.backendcodingchallenge.model.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer,Integer>{

}
