package com.spring.e_commerce.repository;

import com.spring.e_commerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    java.util.Optional<Category> findByName(String name);
}
