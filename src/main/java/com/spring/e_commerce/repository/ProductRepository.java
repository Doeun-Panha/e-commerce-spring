package com.spring.e_commerce.repository;

import com.spring.e_commerce.model.Category;
import com.spring.e_commerce.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category  category);
}
