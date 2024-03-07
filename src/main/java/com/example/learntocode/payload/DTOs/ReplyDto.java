package com.example.learntocode.payload.DTOs;

import com.example.learntocode.models.Reply;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for {@link Reply}
 */
@Data
public class
ReplyDto {
    Long userId;
    String text;
    Long questionId;
    LocalDateTime dateOfCreation;
}