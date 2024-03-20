package com.example.learntocode.payload.DTOs;

import com.example.learntocode.models.User;
import com.example.learntocode.models.enums.Status;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link User}
 */
@Data
public class UserDto {
    Long id;
    String auth0Id;
    String username;
    String nickname;
    @Email
    String email;
    String name;
    String pictureUrl;
    Set<Long> questionIds;
    boolean blocked;
    LocalDateTime createdAt;
    Status status;
}