package com.example.learntocode.payload.DTOs;

import com.example.learntocode.models.Question;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link Question}
 */
@Data
public class QuestionDto {
    long id;
    String title;
    String text;
    Long authorId;
    Set<Long> tagIds;
    Set<Long> replyIds;
    List<String> imageUrls;
    LocalDateTime datePublished;
}