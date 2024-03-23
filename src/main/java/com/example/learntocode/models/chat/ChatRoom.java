//package com.example.learntocode.models.chat;
//
//import com.example.learntocode.models.User;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//@Getter
//@Setter
//@Entity
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "chat_rooms")
//public class ChatRoom {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @ManyToMany
//    @JoinTable(name = "chat_rooms_participants",
//            joinColumns = @JoinColumn(name = "chatRoom_id"),
//            inverseJoinColumns = @JoinColumn(name = "participants_id"))
//    private Set<User> participants = new LinkedHashSet<>();
//
//    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<ChatMessage> chatMessages = new LinkedHashSet<>();
//
//    private LocalDateTime timestamp;
//
//    @PrePersist
//    protected void onCreate() {
//        this.timestamp = LocalDateTime.now();
//    }
//
//}
