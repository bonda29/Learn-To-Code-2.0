package com.example.learntocode.repository;

import com.example.learntocode.models.ChatMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMemoRepository extends JpaRepository<ChatMemo, Long> {
    @Query("SELECT MAX(c.order) FROM ChatMemo c WHERE c.sessionId = ?1")
    Optional<Integer> findMaxOrder (String sessionId);

    Optional<List<ChatMemo>> findBySessionId(String sessionId);

    default ChatMemo saveAndIncrementOrder(ChatMemo chatMemo) {
        Integer maxOrder = findMaxOrder(chatMemo.getSessionId()).orElse(1);
        chatMemo.setOrder(maxOrder + 1);
        return save(chatMemo);
    }

}