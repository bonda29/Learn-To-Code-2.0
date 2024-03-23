package com.example.learntocode.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "direct_message")
public class DirectMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "is_edited", columnDefinition = "boolean default false")
    private boolean isEdited;

    @Column(name = "is_read", columnDefinition = "boolean default false")
    private boolean isRead;

    @PrePersist
    protected void onCreate() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
}
