//package com.example.learntocode.repository;
//
//import com.example.learntocode.models.chat.ChatMessage;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
//    Optional<List<ChatMessage>> findByChatRoomIdOrderByTimestamp(Long chatRoomId);
//}