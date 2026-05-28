package com.ionov.service;

import com.ionov.entity.*;
import com.ionov.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartServiceTest {
    @Autowired private CartRepository cartRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;

    private Role getOrCreateRole(Role.RoleType type) {
        return roleRepository.findByName(type).orElseGet(() -> roleRepository.save(new Role(null, type)));
    }

    @Test
    void testCreateCart() {
        Role role = getOrCreateRole(Role.RoleType.CLIENT);
        User user = new User();
        user.setFirstName("Test"); user.setLastName("Cart");
        user.setEmail("cart@test.com"); user.setPassword("pass");
        user.setRole(role); user.setEnabled(true);
        userRepository.save(user);
        Cart cart = new Cart(); cart.setUser(user);
        assertNotNull(cartRepository.save(cart).getId());
    }

    @Test
    void testAddItemToCart() {
        Role role = getOrCreateRole(Role.RoleType.CLIENT);
        User user = new User();
        user.setFirstName("Test"); user.setLastName("Item");
        user.setEmail("item2@test.com"); user.setPassword("pass");
        user.setRole(role); user.setEnabled(true);
        userRepository.save(user);
        Cart cart = new Cart(); cart.setUser(user); cartRepository.save(cart);
        Category cat = categoryRepository.save(Category.builder().name("TestCat2").build());
        Product product = new Product();
        product.setName("TestProduct2"); product.setPrice(new BigDecimal("100"));
        product.setStock(10); product.setCategory(cat); product.setActive(true);
        productRepository.save(product);
        CartItem item = new CartItem();
        item.setCart(cart); item.setProduct(product); item.setQuantity(2);
        assertNotNull(cartItemRepository.save(item).getId());
    }
}