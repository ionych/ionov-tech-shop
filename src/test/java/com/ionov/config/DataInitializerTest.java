package com.ionov.config;

import com.ionov.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataInitializerTest {
    @Autowired private RoleRepository roleRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;

    @Test
    void testRolesExist() {
        assertTrue(roleRepository.count() > 0);
    }

    @Test
    void testCategoriesExist() {
        assertTrue(categoryRepository.count() > 0);
    }

    @Test
    void testProductsExist() {
        assertTrue(productRepository.count() > 0);
    }

    @Test
    void testAdminExists() {
        assertTrue(userRepository.findByEmail("admin@ionov.ru").isPresent());
    }
}