package com.ionov.controller;

import com.ionov.entity.*;
import com.ionov.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping
    public List<Order> getMyOrders(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        return orderRepository.findByUserId(user.getId());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createOrder(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow();
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Корзина пуста"));
        }

        BigDecimal total = cartItems.stream()
                .map(ci -> ci.getProduct().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(total);
        order.setStatus(Order.OrderStatus.NEW);
        order = orderRepository.save(order);

        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getProduct().getPrice());
            orderItemRepository.save(oi);
        }

        cartItemRepository.deleteByCartId(cart.getId());
        return ResponseEntity.ok(order);
    }
}