package com.example.learntocode.errors;

public class InvalidReplyException extends RuntimeException {
    public InvalidReplyException(String message) {
        super(message);
    }
}