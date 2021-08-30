package io.github.andrielson.spring_boot_forum.exceptions;

public class SubforumNotFoundException extends RuntimeException {
    public SubforumNotFoundException(String message) {
        super(message);
    }
}
