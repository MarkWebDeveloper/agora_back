package de.stella.agora_web;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;

import de.stella.agora_web.roles.model.Role;
import de.stella.agora_web.user.model.User;

public class UserTest {

    @Test
    void testConstructor() {
        Long id = 1L;
        String username = "testUser";
        String password = "testPassword";
        Set<Role> roles = new HashSet<>();
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(roles, user.getRoles());
    }

    @Test
    void testHasRole() {
        User user = new User();
        Role role = Mockito.mock(Role.class);
        Mockito.when(role.getRole()).thenReturn("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        assertTrue(user.hasRole("ROLE_USER"));
        assertFalse(user.hasRole("ROLE_ADMIN"));
    }

    @Test
    void testGetRole() {
        User user = new User();
        Role role = Mockito.mock(Role.class);
        Mockito.when(role.getRole()).thenReturn("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        GrantedAuthority grantedAuthority = user.getAuthority();
        assertNotNull(grantedAuthority);
        assertNotNull(grantedAuthority.getAuthority());
        assertEquals("ROLE_USER", grantedAuthority.getAuthority());
    }

    @Test
    void testSetFavorite() {
        User user = new User();
        assertFalse(user.isFavorite());

        user.setFavorite(true);
        assertTrue(user.isFavorite());

        user.setFavorite(false);
        assertFalse(user.isFavorite());

   
    }

    @Test
    void testIsFavorite() {
        User user = new User();
        user.setFavorite(true);
        assertTrue(user.isFavorite());
        user.setFavorite(false);
        assertFalse(user.isFavorite());
    }
}

