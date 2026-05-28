package com.ionov.controller;

import com.ionov.entity.*;
import com.ionov.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping
    @Transactional
    public ResponseEntity<?> getCart(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        if (user == null) return ResponseEntity.ok(java.util.List.of());
        Cart cart = cartRepository.findByUserId(user.getId()).orElse(null);
        if (cart == null) return ResponseEntity.ok(java.util.List.of());
        return ResponseEntity.ok(cartItemRepository.findByCartId(cart.getId()));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> addItem(@RequestBody Map<String, Object> body, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        if (user == null) return ResponseEntity.badRequest().body(Map.of("error", "Пользователь не найден"));

        Cart cart = cartRepository.findByUserId(user.getId()).orElse(null);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        Product product = productRepository.findById(Long.valueOf(body.get("productId").toString())).orElse(null);
        if (product == null) return ResponseEntity.badRequest().body(Map.of("error", "Товар не найден"));

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(Integer.valueOf(body.get("quantity").toString()));
        item = cartItemRepository.save(item);

        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeItem(@PathVariable Long id) {
        cartItemRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}