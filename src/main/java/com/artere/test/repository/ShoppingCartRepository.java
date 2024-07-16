package com.artere.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artere.test.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}