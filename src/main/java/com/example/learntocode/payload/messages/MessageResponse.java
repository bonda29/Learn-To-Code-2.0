package com.example.learntocode.payload.messages;

import lombok.Value;

@Value
public class MessageResponse {

    String text;

    public static MessageResponse from(final String text) {
        return new MessageResponse(text);
    }
}
