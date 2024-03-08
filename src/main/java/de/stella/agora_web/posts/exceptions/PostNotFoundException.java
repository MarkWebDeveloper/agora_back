package de.stella.agora_web.posts.exceptions;

public class PostNotFoundException extends PostException {
    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
