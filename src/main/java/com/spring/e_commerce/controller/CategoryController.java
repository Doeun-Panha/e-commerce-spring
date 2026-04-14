package com.spring.e_commerce.controller;

import com.spring.e_commerce.model.Category;
import com.spring.e_commerce.model.Product;
import com.spring.e_commerce.repository.CategoryRepository;
import com.spring.e_commerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Category> categories(){
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category addCategory(@RequestBody Category category){
        return categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable int id, @RequestBody Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setName(categoryDetails.getName());
        return categoryRepository.save(category);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Step 1: Find products linked to this category and "free" them
        List<Product> products = productRepository.findByCategory(category);
        for (Product product : products) {
            product.setCategory(null);
        }
        productRepository.saveAll(products);

        // Step 2: Now delete the category
        categoryRepository.delete(category);
        return ResponseEntity.ok().build();
    }
}
