package com.artere.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artere.test.model.Category;
import com.artere.test.model.Product;
import com.artere.test.repository.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    category.setDescription(updatedCategory.getDescription());
                    return categoryRepository.save(category);
                }).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public void linkProductToCategory(Category category, Product product) {
        category.getProducts().add(product);
        product.setCategory(category);
        categoryRepository.save(category);
    }

    public void unlinkProductFromCategory(Category category, Product product) {
        category.getProducts().remove(product);
        product.setCategory(null);
        categoryRepository.save(category);
    }
}