package com.example.learntocode.repository;

import com.example.learntocode.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.tags WHERE q.id = :id")
    Optional<Question> findByIdWithTags(@Param("id") Long id);


}
