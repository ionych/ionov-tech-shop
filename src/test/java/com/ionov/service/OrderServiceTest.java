package com.ionov.service;

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
class OrderServiceTest {
    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    @Test
    void testCreateOrder() {
        Role role = roleRepository.findByName(Role.RoleType.CLIENT).orElseGet(() -> roleRepository.save(new Role(null, Role.RoleType.CLIENT)));
        User user = new User();
        user.setFirstName("Order"); user.setLastName("Test");
        user.setEmail("order2@test.com"); user.setPassword("pass");
        user.setRole(role); user.setEnabled(true);
        userRepository.save(user);
        Order order = new Order();
        order.setUser(user); order.setTotalPrice(new BigDecimal("5000"));
        order.setStatus(Order.OrderStatus.NEW);
        assertNotNull(orderRepository.save(order).getId());
    }
}