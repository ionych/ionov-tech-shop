package com.ionov.repository;

import com.ionov.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RoleRepositoryTest {
    @Autowired private RoleRepository roleRepository;

    @Test
    void testFindByName() {
        Role found = roleRepository.findByName(Role.RoleType.ADMIN).orElse(null);
        assertNotNull(found);
        assertEquals(Role.RoleType.ADMIN, found.getName());
    }
}