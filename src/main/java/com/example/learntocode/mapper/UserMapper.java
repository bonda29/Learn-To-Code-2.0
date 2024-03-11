package com.example.learntocode.mapper;

import com.example.learntocode.models.Question;
import com.example.learntocode.models.User;
import com.example.learntocode.payload.DTOs.UserDto;
import com.example.learntocode.repository.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;
    private final QuestionRepository questionRepository;

    public UserMapper(ModelMapper modelMapper, QuestionRepository questionRepository) {
        this.modelMapper = modelMapper;
        this.questionRepository = questionRepository;
    }

    public UserDto toDto(User user) {
        UserDto dto = modelMapper.map(user, UserDto.class);
        Set<Long> questionIds = user.getQuestions().stream()
                .map(Question::getId)
                .collect(Collectors.toSet());

        dto.setQuestionIds(questionIds);

        return dto;
    }

    public List<UserDto> toDto(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public User toEntity(UserDto dto) {
        User entity = modelMapper.map(dto, User.class);

        Set<Question> questions = dto.getQuestionIds().stream()
                .map(id -> findById(questionRepository, id))
                .collect(Collectors.toSet());

        entity.setQuestions(questions);

        return entity;
    }

    public User toEntity(UserDto dto, User user) {
        modelMapper.map(dto, user);
        return user;
    }
}
