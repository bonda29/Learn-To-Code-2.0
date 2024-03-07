package com.example.learntocode.mapper;


import com.example.learntocode.models.Question;
import com.example.learntocode.models.Tag;
import com.example.learntocode.payload.DTOs.QuestionDto;
import com.example.learntocode.repository.TagRepository;
import com.example.learntocode.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Component
public class QuestionMapper {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;


    public QuestionMapper(ModelMapper modelMapper, UserRepository userRepository, TagRepository tagRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.modelMapper.addMappings(new PropertyMap<Question, QuestionDto>() {
            @Override
            protected void configure() {
                map().setAuthorId(source.getAuthor().getId());

                if (source.getTags() != null) {
                    map().setTagIds(source.getTags().stream()
                            .map(Tag::getId)
                            .collect(Collectors.toSet()));
                }
            }
        });
    }

    public QuestionDto toDto(Question question) {
        return modelMapper.map(question, QuestionDto.class);
    }

    public List<QuestionDto> toDto(List<Question> questions) {
        return questions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Question toEntity(QuestionDto questionDto) {
        Question question = modelMapper.map(questionDto, Question.class);
        question.setAuthor(findById(userRepository, questionDto.getAuthorId()));

        if (questionDto.getTagIds() != null) {
            question.setTags(questionDto.getTagIds().stream()
                    .map(id -> findById(tagRepository, id))
                    .collect(Collectors.toSet()));
        }

        return question;
    }

    public Question toEntity(QuestionDto questionDto, Question question) {
        modelMapper.map(questionDto, question);
        question.setAuthor(findById(userRepository, questionDto.getAuthorId()));

        if (questionDto.getTagIds() != null) {
            question.setTags(questionDto.getTagIds().stream()
                    .map(id -> findById(tagRepository, id))
                    .collect(Collectors.toSet()));
        }
        return question;
    }
}

