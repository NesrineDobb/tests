package com.challenge.backendcodingchallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.backendcodingchallenge.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
