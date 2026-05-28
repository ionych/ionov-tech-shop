package com.ionov.controller;

import com.ionov.entity.Product;
import com.ionov.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findByActiveTrue();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String q) {
        return productRepository.findByNameContainingIgnoreCase(q);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> byCategory(@PathVariable Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}