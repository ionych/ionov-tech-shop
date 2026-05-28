package com.ionov.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtAuthFilterTest {
    @Autowired private JwtUtils jwtUtils;
    @Autowired private JwtAuthFilter jwtAuthFilter;

    @Test
    void testFilterExists() {
        assertNotNull(jwtAuthFilter);
    }

    @Test
    void testTokenRoundTrip() {
        String token = jwtUtils.generateToken("filter@test.com", "CLIENT");
        assertTrue(jwtUtils.validateToken(token));
        assertEquals("filter@test.com", jwtUtils.getEmailFromToken(token));
        assertEquals("CLIENT", jwtUtils.getRoleFromToken(token));
    }

    @Test
    void testInvalidToken() {
        assertFalse(jwtUtils.validateToken("invalid.token.here"));
        assertFalse(jwtUtils.validateToken(""));
        assertFalse(jwtUtils.validateToken(null));
    }
}