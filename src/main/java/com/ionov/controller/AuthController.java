package com.ionov.controller;

import com.ionov.entity.*;
import com.ionov.repository.*;
import com.ionov.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtils jwtUtils;
    @Autowired private CartRepository cartRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        String firstName = body.get("firstName");
        String lastName = body.get("lastName");

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email уже занят"));
        }

        Role clientRole = roleRepository.findByName(Role.RoleType.CLIENT)
                .orElseThrow(() -> new RuntimeException("Роль CLIENT не найдена"));

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .firstName(firstName)
                .lastName(lastName)
                .role(clientRole)
                .enabled(true)
                .build();
        userRepository.save(user);

        Cart cart = Cart.builder().user(user).build();
        cartRepository.save(cart);

        String token = jwtUtils.generateToken(email, "CLIENT");
        return ResponseEntity.ok(Map.of("token", token, "message", "Регистрация успешна"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Неверный пароль"));
        }

        String token = jwtUtils.generateToken(email, user.getRole().getName().toString());
        return ResponseEntity.ok(Map.of("token", token, "role", user.getRole().getName().toString()));
    }
}