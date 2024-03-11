package com.example.learntocode.mapper;


import com.example.learntocode.models.Question;
import com.example.learntocode.models.Tag;
import com.example.learntocode.payload.DTOs.TagDto;
import com.example.learntocode.repository.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Component
public class TagMapper {
    private final ModelMapper modelMapper;
    private final QuestionRepository questionRepository;

    public TagMapper(ModelMapper modelMapper, QuestionRepository questionRepository) {
        this.modelMapper = modelMapper;
        this.questionRepository = questionRepository;

        this.modelMapper.addMappings(new PropertyMap<Tag, TagDto>() {
            @Override
            protected void configure() {

            }
        });
    }

    public TagDto toDto(Tag tag) {
        TagDto tagDto = modelMapper.map(tag, TagDto.class);

        Set<Long> questionIds = tag.getQuestions().stream()
                .map(Question::getId)
                .collect(Collectors.toSet());

        tagDto.setQuestionIds(questionIds);

        return tagDto;
    }

    public List<TagDto> toDto(List<Tag> tags) {
        return tags.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Tag toEntity(TagDto dto) {
        Tag tag = modelMapper.map(dto, Tag.class);
        if (dto.getQuestionIds() != null) {
            tag.setQuestions(dto.getQuestionIds().stream()
                    .map(id -> findById(questionRepository, id))
                    .collect(Collectors.toSet()));
        }
        return tag;
    }
}
