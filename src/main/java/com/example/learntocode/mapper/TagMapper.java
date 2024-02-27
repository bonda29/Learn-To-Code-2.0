package com.example.learntocode.mapper;


import com.example.learntocode.models.Tag;
import com.example.learntocode.payload.DTOs.TagDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagMapper {
    private final ModelMapper modelMapper;

    public TagMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TagDto toDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }

    public List<TagDto> toDto(List<Tag> tags) {
        return tags.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Tag toEntity(TagDto dto) {
        return modelMapper.map(dto, Tag.class);
    }
}
