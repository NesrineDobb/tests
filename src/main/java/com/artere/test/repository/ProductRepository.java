package com.artere.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artere.test.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
