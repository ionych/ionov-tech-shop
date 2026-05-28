package com.ionov.controller;

import com.ionov.entity.*;
import com.ionov.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthControllerTest {
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Test
    void testRegisterUser() {
        Role client = roleRepository.findByName(Role.RoleType.CLIENT).orElseGet(() -> roleRepository.save(new Role(null, Role.RoleType.CLIENT)));
        User user = User.builder().firstName("Test").lastName("User").email("test@test.com").password(passwordEncoder.encode("pass")).role(client).enabled(true).build();
        User saved = userRepository.save(user);
        assertNotNull(saved.getId());
    }

    @Test
    void testLoginCheck() {
        Role client = roleRepository.findByName(Role.RoleType.CLIENT).orElseGet(() -> roleRepository.save(new Role(null, Role.RoleType.CLIENT)));
        User user = User.builder().firstName("Test2").lastName("User2").email("test2@test.com").password(passwordEncoder.encode("pass")).role(client).enabled(true).build();
        userRepository.save(user);
        User found = userRepository.findByEmail("test2@test.com").orElse(null);
        assertNotNull(found);
        assertTrue(passwordEncoder.matches("pass", found.getPassword()));
    }
}