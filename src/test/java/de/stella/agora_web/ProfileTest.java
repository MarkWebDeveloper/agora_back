package de.stella.agora_web;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.stella.agora_web.profiles.model.Profile;

public class ProfileTest {

    private Profile profile;

    private Profile testProfile;

    @BeforeEach
    public void setUp() {
        testProfile = new Profile();
    }

    @Test
    public void testHasRole() {
        assertEquals(true, testProfile.hasRole("Friend"));
    }

    @Test
    public void testIsFavorite() {
        assertEquals(false, testProfile.isFavorite());
    }

    @Test
    public void testSetFavorite() {
        testProfile.setFavorite(true);
        assertEquals(true, testProfile.isFavorite());
    }
}
