package com.example.learntocode.models;

import com.theokanning.openai.completion.chat.ChatMessageRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "ai_chat_memo")
public class AiChatMemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Must be either 'system', 'user', 'assistant' or 'function'.<br>
     * You may use {@link ChatMessageRole} enum.
     */
    private ChatMessageRole role;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "chat_order")
    private int order;

    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
