package com.spring.e_commerce.controller;

import com.spring.e_commerce.model.Product;
import com.spring.e_commerce.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository repository;

    public ProductController(ProductRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Product> getProducts(){
        return repository.findAll();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product){ //convert JSON body from flutter to Java product
        return repository.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails){
        Product product=repository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not found with id: "+id));

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        return repository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        repository.deleteById(id);
    }
}
