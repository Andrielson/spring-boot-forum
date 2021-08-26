package io.github.andrielson.spring_boot_forum.exceptions;

public class ForumException extends RuntimeException {
    public ForumException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
