package de.stella.agora_web;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.stella.agora_web.posts.model.Post;
import de.stella.agora_web.replys.model.Reply;
import de.stella.agora_web.user.model.User;

class ReplyTest {
    private Reply reply;

    @BeforeEach
    void setUp() {
        reply = new Reply(1L, "Test title", "Test message", LocalDateTime.now(), new User(), new Post());
    }

    @Test
    void testId() {
        assertEquals(1L, reply.getId());
    }

    @Test
    void testTitle() {
        assertEquals("Test title", reply.getTitle());
    }

    @Test
    void testMessage() {
        assertEquals("Test message", reply.getMessage());
    }

    @Test
    void testCreationDate() {
        assertNotNull(reply.getCreationDate());
    }

    @Test
    void testAuthor() {
        assertNotNull(reply.getAuthor());
    }

    @Test
    void testPosts() {
        assertNotNull(reply.getPost());
    }
}