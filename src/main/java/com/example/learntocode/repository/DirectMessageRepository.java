package com.example.learntocode.repository;

import com.example.learntocode.models.DirectMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
    Optional<List<DirectMessage>> findAllBySenderIdAndReceiverId(Long senderId, Long receiverId);
}