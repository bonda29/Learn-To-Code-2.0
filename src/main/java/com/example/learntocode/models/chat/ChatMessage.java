//package com.example.learntocode.models.chat;
//
//import com.example.learntocode.models.User;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@Entity
//@NoArgsConstructor
//@Table(name = "chat_messages")
//public class ChatMessage {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "chat_room_id", nullable = false)
//    private ChatRoom chatRoom;
//
//    @ManyToOne
//    @JoinColumn(name = "sender_id")
//    private User sender;
//
//    @ManyToOne
//    @JoinColumn(name = "recipient_id")
//    private User recipient;
//
//    private String content;
//
//    @Column(name = "is_edited", columnDefinition = "boolean default false")
//    private boolean isEdited;
//
//    @Column(name = "is_read", columnDefinition = "boolean default false")
//    private boolean isRead;
//
//    private LocalDateTime timestamp;
//
//    @PrePersist
//    protected void onCreate() {
//        this.timestamp = LocalDateTime.now();
//    }
//
//}
