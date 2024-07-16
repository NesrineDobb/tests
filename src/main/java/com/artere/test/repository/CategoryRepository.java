package com.artere.test.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.artere.test.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
