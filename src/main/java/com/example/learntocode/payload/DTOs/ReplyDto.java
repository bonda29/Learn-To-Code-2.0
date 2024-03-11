package com.example.learntocode.payload.DTOs;

import com.example.learntocode.models.Reply;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link Reply}
 */
@Data
public class
ReplyDto {
    Long id;
    Long authorId;
    String text;
    Long questionId;
    Long parentReplyId;
    Set<ReplyDto> childReplies;
    LocalDateTime dateOfCreation;
}