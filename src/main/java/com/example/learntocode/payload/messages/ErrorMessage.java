package com.example.learntocode.payload.messages;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ErrorMessage {

    String message;
    LocalDateTime timestamp;
    String details;
    String errorCode;
    String path;

    public ErrorMessage(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.details = "";
        this.errorCode = "";
        this.path = "";
    }

    public ErrorMessage(String message, String details, String errorCode, String path) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.details = details;
        this.errorCode = errorCode;
        this.path = path;
    }


    public static ErrorMessage from(final String message) {
        return new ErrorMessage(message);
    }

    public static ErrorMessage from(final String message, final String details, final String errorCode, final String path) {
        return new ErrorMessage(message, details, errorCode, path);
    }
}
