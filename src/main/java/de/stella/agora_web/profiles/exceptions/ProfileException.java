package de.stella.agora_web.profiles.exceptions;

public class ProfileException extends RuntimeException {
    public ProfileException(String message) {
        super(message);
    }

    public ProfileException(String message, Throwable cause) {
        super(message, cause);
    }

}
