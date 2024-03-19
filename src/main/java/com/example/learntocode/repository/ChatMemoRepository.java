package com.example.learntocode.repository;

import com.example.learntocode.models.AiChatMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMemoRepository extends JpaRepository<AiChatMemo, Long> {
    @Query("SELECT MAX(c.order) FROM AiChatMemo c WHERE c.sessionId = ?1")
    Optional<Integer> findMaxOrder(String sessionId);

    Optional<List<AiChatMemo>> findAllByAuthorId(Long authorId);

    @Query("SELECT c FROM AiChatMemo c " +
            "WHERE c.author.id = :userId AND c.order = " +
            "(SELECT MIN(c2.order) FROM AiChatMemo c2 WHERE c2.sessionId = c.sessionId AND c2.author.id = :userId)")
    List<AiChatMemo> findFirstMessageOfEachSessionByUserId(@Param("userId") Long userId);

    Optional<List<AiChatMemo>> findBySessionId(String sessionId);

    default AiChatMemo saveAndIncrementOrder(AiChatMemo aiChatMemo) {
        Integer maxOrder = findMaxOrder(aiChatMemo.getSessionId()).orElse(1);
        aiChatMemo.setOrder(maxOrder + 1);
        return save(aiChatMemo);
    }


}