package com.example.learntocode.payload.DTOs;

import com.example.learntocode.models.chat.AiChatMemo;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for {@link AiChatMemo}
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