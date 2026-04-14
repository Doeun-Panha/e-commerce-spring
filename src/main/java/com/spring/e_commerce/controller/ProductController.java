package com.spring.e_commerce.controller;

import com.spring.e_commerce.model.Category;
import com.spring.e_commerce.model.Product;
import com.spring.e_commerce.repository.CategoryRepository;
import com.spring.e_commerce.repository.ProductRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository repository, CategoryRepository categoryRepository){
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Product> getProducts(){
        return repository.findAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> addProduct(
            @ModelAttribute Product product,
            @RequestParam("categoryId") int categoryId,
            @RequestParam("image") MultipartFile image) throws IOException {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

        // 1. Save the file to a local folder
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path path = Paths.get("user-photos/" + fileName);
        Files.createDirectories(path.getParent());
        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // 2. Set the URL to the local path for the database
        product.setImageUrl("/uploads/" + fileName);

        return ResponseEntity.ok(repository.save(product));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> updateProduct(
            @PathVariable int id,
            @ModelAttribute Product product,
            @RequestParam("categoryId") int categoryId,
            @RequestParam(value="image", required = false) MultipartFile image) throws IOException{
        Product existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 1. Link the Category
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existing.setCategory(category); // This sets the foreign key in the DB

        // 2. Handle Image
        if(image != null && !image.isEmpty()){
            // Save new file and update path
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path path = Paths.get("user-photos/" + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            existing.setImageUrl("/uploads/" + fileName);
        }
        // 3. Update other fields
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setDescription(product.getDescription());
        existing.setStockQuantity(product.getStockQuantity());
        existing.setLowStockThreshold(product.getLowStockThreshold());

        return ResponseEntity.ok(repository.save(existing));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id){
        repository.deleteById(id);
    }
}
