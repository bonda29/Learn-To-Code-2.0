package com.example.learntocode.payload.DTOs;

import com.example.learntocode.models.Tag;
import lombok.Data;

/**
 * DTO for {@link Tag}
 */

@Data
public class TagDto {
    Long id;
    String name;
}