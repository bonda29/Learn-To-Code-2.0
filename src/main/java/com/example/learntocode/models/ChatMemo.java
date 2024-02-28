package com.example.learntocode.models;

import com.theokanning.openai.completion.chat.ChatMessageRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "chat_memo")
public class ChatMemo {
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
