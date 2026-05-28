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
class ProductControllerTest {
    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;

    @Test
    void testFindActiveProducts() {
        Category cat = categoryRepository.save(Category.builder().name("CtrlTest").build());
        Product p = new Product();
        p.setName("CtrlProduct");
        p.setPrice(new BigDecimal("100"));
        p.setStock(5);
        p.setCategory(cat);
        p.setActive(true);
        productRepository.save(p);

        assertFalse(productRepository.findByActiveTrue().isEmpty());
    }

    @Test
    void testSearchByName() {
        Category cat = categoryRepository.save(Category.builder().name("SearchCtrl").build());
        Product p = new Product();
        p.setName("УникальныйПоиск");
        p.setPrice(new BigDecimal("200"));
        p.setStock(3);
        p.setCategory(cat);
        p.setActive(true);
        productRepository.save(p);

        assertFalse(productRepository.findByNameContainingIgnoreCase("Уникальный").isEmpty());
    }
}