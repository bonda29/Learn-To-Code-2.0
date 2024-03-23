//package com.example.learntocode.repository;
//
//import com.example.learntocode.models.chat.ChatRoom;
//import com.example.learntocode.models.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//import java.util.Set;
//
//@Repository
//public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
//
//    Optional<ChatRoom> findByParticipants(Set<User> participants);
//}