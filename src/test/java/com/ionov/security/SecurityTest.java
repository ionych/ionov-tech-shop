package com.ionov.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityTest {
    @Autowired private JwtUtils jwtUtils;

    @Test
    void testGenerateAndValidateToken() {
        String token = jwtUtils.generateToken("test@test.com", "CLIENT");
        assertNotNull(token);
        assertTrue(jwtUtils.validateToken(token));
        assertEquals("test@test.com", jwtUtils.getEmailFromToken(token));
        assertEquals("CLIENT", jwtUtils.getRoleFromToken(token));
    }
}