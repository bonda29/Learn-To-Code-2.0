package com.example.learntocode.mapper;


import com.example.learntocode.models.Question;
import com.example.learntocode.models.Reply;
import com.example.learntocode.models.Tag;
import com.example.learntocode.payload.DTOs.QuestionDto;
import com.example.learntocode.repository.ReplyRepository;
import com.example.learntocode.repository.TagRepository;
import com.example.learntocode.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Component
public class QuestionMapper {

    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;


    public QuestionMapper(ModelMapper modelMapper, TagRepository tagRepository, ReplyRepository replyRepository,
                          UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
    }

    public QuestionDto toDto(Question question) {
        QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);

        questionDto.setAuthorId(question.getAuthor().getId());

        Set<Long> tagIds = question.getTags().stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());
        questionDto.setTagIds(tagIds);

        Set<Long> replyIds = question.getReplies().stream()
                .map(Reply::getId)
                .collect(Collectors.toSet());
        questionDto.setReplyIds(replyIds);

        return questionDto;
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

        if (questionDto.getReplyIds() != null) {
            question.setReplies(questionDto.getReplyIds().stream()
                    .map(id -> findById(replyRepository, id))
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

        if (questionDto.getReplyIds() != null) {
            question.setReplies(questionDto.getReplyIds().stream()
                    .map(id -> findById(replyRepository, id))
                    .collect(Collectors.toSet()));
        }

        return question;
    }
}

