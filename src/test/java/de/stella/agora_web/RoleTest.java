package de.stella.agora_web;


import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.stella.agora_web.roles.model.Role;
import de.stella.agora_web.user.model.User;

class RoleTest {

    @Mock
    private User user1;

    @Mock
    private User user2;

    private Set<User> users;

    private Role role;

    @Test
    void testGetRole() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        String roleName = "Admin";
        users = new HashSet<>();
        users.add(user1);
        users.add(user2);

        role = Role.builder()
                .id(1L)
                .name(roleName)
                .users(users)
                .build();

        // Act
        String actualRole = role.getRole();

        // Assert
        assertEquals(roleName, actualRole);
    }
}