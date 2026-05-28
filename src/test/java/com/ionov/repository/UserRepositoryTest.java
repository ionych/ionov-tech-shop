package com.ionov.repository;

import com.ionov.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    private Role getOrCreateRole(Role.RoleType type) {
        return roleRepository.findByName(type).orElseGet(() -> roleRepository.save(new Role(null, type)));
    }

    @Test
    void testFindByEmail() {
        Role role = getOrCreateRole(Role.RoleType.CLIENT);
        User user = new User();
        user.setFirstName("Repo"); user.setLastName("Test");
        user.setEmail("repo2@test.com"); user.setPassword("pass");
        user.setRole(role); user.setEnabled(true);
        userRepository.save(user);
        assertNotNull(userRepository.findByEmail("repo2@test.com").orElse(null));
    }

    @Test
    void testExistsByEmail() {
        Role role = getOrCreateRole(Role.RoleType.CLIENT);
        User user = new User();
        user.setFirstName("Exists"); user.setLastName("Test");
        user.setEmail("exists2@test.com"); user.setPassword("pass");
        user.setRole(role); user.setEnabled(true);
        userRepository.save(user);
        assertTrue(userRepository.existsByEmail("exists2@test.com"));
    }
}