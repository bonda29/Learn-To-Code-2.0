package com.example.learntocode.payload.DTOs;

import com.example.learntocode.models.ChatMemo;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link ChatMemo}
 */
@Data
public class ChatMemoDto {
    ChatMessageRole role;
    String content;
    Long authorId;
    String sessionId;
    int order;
    LocalDateTime timestamp;
}