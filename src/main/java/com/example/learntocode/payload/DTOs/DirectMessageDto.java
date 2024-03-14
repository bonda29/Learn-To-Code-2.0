package com.example.learntocode.payload.DTOs;

import com.example.learntocode.models.DirectMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link DirectMessage}
 */
@Getter
@Setter
@NoArgsConstructor
public class DirectMessageDto implements Serializable {
    Long senderId;
    Long receiverId;
    String content;
    Timestamp timestamp;
    boolean isEdited;
}