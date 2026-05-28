package com.ionov.controller;

import com.ionov.entity.*;
import com.ionov.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartControllerTest {
    @Autowired private CartRepository cartRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;

    @Test
    void testFindCartByUser() {
        Role role = roleRepository.findByName(Role.RoleType.CLIENT).orElseGet(() -> roleRepository.save(new Role(null, Role.RoleType.CLIENT)));
        User user = new User();
        user.setFirstName("Cart"); user.setLastName("Test");
        user.setEmail("cartctrl@test.com"); user.setPassword("pass");
        user.setRole(role); user.setEnabled(true);
        userRepository.save(user);

        Cart cart = new Cart(); cart.setUser(user);
        cartRepository.save(cart);

        Cart found = cartRepository.findByUserId(user.getId()).orElse(null);
        assertNotNull(found);
    }

    @Test
    void testAddAndRemoveItem() {
        Role role = roleRepository.findByName(Role.RoleType.CLIENT).orElseGet(() -> roleRepository.save(new Role(null, Role.RoleType.CLIENT)));
        User user = new User();
        user.setFirstName("Item"); user.setLastName("Test");
        user.setEmail("itemctrl@test.com"); user.setPassword("pass");
        user.setRole(role); user.setEnabled(true);
        userRepository.save(user);

        Cart cart = new Cart(); cart.setUser(user); cartRepository.save(cart);
        Category cat = categoryRepository.save(Category.builder().name("CartCat").build());
        Product p = new Product();
        p.setName("CartProduct"); p.setPrice(new java.math.BigDecimal("100"));
        p.setStock(5); p.setCategory(cat); p.setActive(true);
        productRepository.save(p);

        CartItem item = new CartItem();
        item.setCart(cart); item.setProduct(p); item.setQuantity(1);
        cartItemRepository.save(item);

        assertFalse(cartItemRepository.findByCartId(cart.getId()).isEmpty());
        cartItemRepository.deleteById(item.getId());
        assertTrue(cartItemRepository.findByCartId(cart.getId()).isEmpty());
    }
}