package com.example.learntocode.models.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ChatNotification {
    private String id;
    private String senderId;
    private String recipientId;
    private String content;
}
