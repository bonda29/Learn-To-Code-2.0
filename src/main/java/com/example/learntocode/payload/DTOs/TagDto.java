package com.example.learntocode.payload.DTOs;

import com.example.learntocode.models.Tag;
import lombok.Data;

import java.util.Set;

/**
 * DTO for {@link Tag}
 */

@Data
public class TagDto {
    Long id;
    String name;
    Set<Long> questionIds;
}