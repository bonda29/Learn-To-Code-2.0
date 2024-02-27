package com.example.learntocode.payload.DTOs;

import com.example.learntocode.models.User;
import jakarta.validation.constraints.Email;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link User}
 */
@Value
public class UserDto {
    Long id;
    String userId;
    String username;
    String nickname;
    @Email
    String email;
    String name;
    String pictureUrl;
    boolean blocked;
    LocalDateTime createdAt;
}