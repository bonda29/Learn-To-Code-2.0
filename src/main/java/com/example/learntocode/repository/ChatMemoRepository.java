package com.example.learntocode.repository;

import com.example.learntocode.models.AiChatMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMemoRepository extends JpaRepository<AiChatMemo, Long> {
    @Query("SELECT MAX(c.order) FROM AiChatMemo c WHERE c.sessionId = ?1")
    Optional<Integer> findMaxOrder (String sessionId);

    Optional<List<AiChatMemo>> findBySessionId(String sessionId);

    default AiChatMemo saveAndIncrementOrder(AiChatMemo aiChatMemo) {
        Integer maxOrder = findMaxOrder(aiChatMemo.getSessionId()).orElse(1);
        aiChatMemo.setOrder(maxOrder + 1);
        return save(aiChatMemo);
    }

}