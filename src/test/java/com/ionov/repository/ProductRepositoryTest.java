package com.ionov.repository;

import com.ionov.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductRepositoryTest {
    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;

    @Test
    void testFindByActiveTrue() {
        Category cat = categoryRepository.save(Category.builder().name("TestCat").build());
        Product p = new Product();
        p.setName("ActiveProduct");
        p.setPrice(new BigDecimal("100"));
        p.setStock(5);
        p.setCategory(cat);
        p.setActive(true);
        productRepository.save(p);

        List<Product> active = productRepository.findByActiveTrue();
        assertFalse(active.isEmpty());
    }

    @Test
    void testFindByCategoryId() {
        Category cat = categoryRepository.save(Category.builder().name("CatTest").build());
        Product p = new Product();
        p.setName("CatProduct");
        p.setPrice(new BigDecimal("200"));
        p.setStock(3);
        p.setCategory(cat);
        p.setActive(true);
        productRepository.save(p);

        List<Product> result = productRepository.findByCategoryId(cat.getId());
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindByNameContaining() {
        Category cat = categoryRepository.save(Category.builder().name("SearchCat").build());
        Product p = new Product();
        p.setName("УникальныйТовар123");
        p.setPrice(new BigDecimal("300"));
        p.setStock(1);
        p.setCategory(cat);
        p.setActive(true);
        productRepository.save(p);

        List<Product> result = productRepository.findByNameContainingIgnoreCase("уникальный");
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindAllProducts() {
        assertNotNull(productRepository.findAll());
    }
}