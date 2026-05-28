package com.ionov.controller;

import com.ionov.entity.*;
import com.ionov.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductControllerMvcTest {
    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;

    @Test
    void testProductsExist() {
        Category cat = categoryRepository.save(Category.builder().name("TestCat3").build());
        Product p = new Product();
        p.setName("TestProduct3"); p.setPrice(new BigDecimal("100"));
        p.setStock(5); p.setCategory(cat); p.setActive(true);
        productRepository.save(p);

        assertFalse(productRepository.findByActiveTrue().isEmpty());
    }

    @Test
    void testSearchWorks() {
        Category cat = categoryRepository.save(Category.builder().name("SearchCat3").build());
        Product p = new Product();
        p.setName("SuperUnique3"); p.setPrice(new BigDecimal("200"));
        p.setStock(3); p.setCategory(cat); p.setActive(true);
        productRepository.save(p);

        assertFalse(productRepository.findByNameContainingIgnoreCase("Super").isEmpty());
    }
}