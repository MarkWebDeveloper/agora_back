package de.stella.agora_web;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.stella.agora_web.posts.model.Post;
import de.stella.agora_web.user.model.User;

public class PostTest {

    private Post post;

    @BeforeEach
    public void setUp() {
        post = new Post();
    }

    // @Test
    // public void testGetters() {
    //     assertEquals(1L, post.getId());
    //     assertEquals("Test Title", post.getTitle());
    //     assertEquals("Test Message", post.getMessage());
    //     assertNotNull(post.getCreationDate());
    //     assertNotNull(post.getReply());
    //     assertNotNull(post.getUser());
    // }

    @Test
    public void testSetters() {
        User newUser = new User();
        post.setUser(newUser);
        assertEquals(newUser, post.getUser());
    }
}