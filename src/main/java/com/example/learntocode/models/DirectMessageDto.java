package com.example.learntocode.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * DTO for {@link DirectMessage}
 */
@Getter
@Setter
@NoArgsConstructor
public class DirectMessageDto {
    Long senderId;
    Long receiverId;
    String content;
    Timestamp timestamp;
    boolean isEdited;
}