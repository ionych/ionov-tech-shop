package com.ionov.config;

import com.ionov.entity.*;
import com.ionov.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(null, Role.RoleType.CLIENT));
            roleRepository.save(new Role(null, Role.RoleType.ADMIN));
        }

        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName(Role.RoleType.ADMIN).get();
            User admin = User.builder()
                    .firstName("ION")
                    .lastName("Admin")
                    .email("admin@ionov.ru")
                    .password(passwordEncoder.encode("admin"))
                    .role(adminRole)
                    .enabled(true)
                    .build();
            userRepository.save(admin);

            Cart adminCart = Cart.builder().user(admin).build();
            cartRepository.save(adminCart);
        }

        if (categoryRepository.count() == 0) {
            categoryRepository.save(Category.builder().name("Смартфоны").build());
            categoryRepository.save(Category.builder().name("Ноутбуки").build());
            categoryRepository.save(Category.builder().name("Наушники").build());
            categoryRepository.save(Category.builder().name("Планшеты").build());
            categoryRepository.save(Category.builder().name("Аксессуары").build());
        }

        if (productRepository.count() == 0) {
            Category smartphones = categoryRepository.findById(1L).orElse(null);
            Category laptops = categoryRepository.findById(2L).orElse(null);
            Category headphones = categoryRepository.findById(3L).orElse(null);
            Category tablets = categoryRepository.findById(4L).orElse(null);
            Category accessories = categoryRepository.findById(5L).orElse(null);

            productRepository.save(Product.builder().name("iPhone 17 Pro").description("Флагманский смартфон Apple 2026, 512GB, iOS 26").price(new BigDecimal("129990")).stock(10).category(smartphones).imageUrl("https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=400").build());
            productRepository.save(Product.builder().name("Samsung Galaxy S26 Ultra").description("Флагманский смартфон Samsung 2026, 1TB").price(new BigDecimal("129990")).stock(15).category(smartphones).imageUrl("https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=400").build());
            productRepository.save(Product.builder().name("MacBook Pro 16 M5 Pro").description("Ноутбук Apple M5 Pro, 48GB RAM, 2026").price(new BigDecimal("279990")).stock(5).category(laptops).imageUrl("https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400").build());
            productRepository.save(Product.builder().name("ASUS ROG Strix 2026").description("Игровой ноутбук, RTX 5080, 32GB RAM").price(new BigDecimal("189990")).stock(3).category(laptops).imageUrl("https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=400").build());
            productRepository.save(Product.builder().name("AirPods Pro (новейшая модель)").description("Беспроводные наушники Apple").price(new BigDecimal("24990")).stock(25).category(headphones).imageUrl("https://images.unsplash.com/photo-1588423771073-b8903fbb85b5?w=400").build());
            productRepository.save(Product.builder().name("Sony WH-1000XM6").description("Премиальные наушники с шумоподавлением 2026").price(new BigDecimal("34990")).stock(10).category(headphones).imageUrl("https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400").build());
            productRepository.save(Product.builder().name("iPad Pro M5").description("Планшет Apple 2026, 13 OLED").price(new BigDecimal("109990")).stock(8).category(tablets).imageUrl("https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=400").build());
            productRepository.save(Product.builder().name("Samsung Galaxy Tab S11").description("Планшет Samsung 2026, 14.6").price(new BigDecimal("89990")).stock(6).category(tablets).imageUrl("https://images.unsplash.com/photo-1561154464-82e9adf32764?w=400").build());
            productRepository.save(Product.builder().name("Зарядное устройство GaN 150W").description("Сверхбыстрая зарядка").price(new BigDecimal("3990")).stock(40).category(accessories).imageUrl("https://images.unsplash.com/photo-1583863788434-e58a36330cf0?w=400").build());
            productRepository.save(Product.builder().name("Чехол для iPhone 17 Pro").description("Силиконовый чехол MagSafe 2026").price(new BigDecimal("2490")).stock(80).category(accessories).imageUrl("https://images.unsplash.com/photo-1601784551446-20c9e07cdbdb?w=400").build());
        }
    }
}