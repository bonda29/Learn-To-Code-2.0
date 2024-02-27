package com.example.learntocode.mapper;


import com.example.learntocode.models.Reply;
import com.example.learntocode.payload.DTOs.ReplyDto;
import com.example.learntocode.repository.QuestionRepository;
import com.example.learntocode.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Component
public class ReplyMapper {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public ReplyMapper(ModelMapper modelMapper, UserRepository userRepository, QuestionRepository questionRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.modelMapper.addMappings(new PropertyMap<Reply, ReplyDto>() {
            @Override
            protected void configure() {
                map().setUserId(source.getAuthor().getId());
                map().setQuestionId(source.getQuestion().getId());
            }
        });
    }

    public ReplyDto toDto(Reply reply) {
        return modelMapper.map(reply, ReplyDto.class);
    }

    public List<ReplyDto> toDto(List<Reply> replies) {

        return replies.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Reply toEntity(ReplyDto replyDto) {
        Reply reply = modelMapper.map(replyDto, Reply.class);
        reply.setAuthor(findById(userRepository, replyDto.getUserId()));
        reply.setQuestion(findById(questionRepository, replyDto.getQuestionId()));
        return reply;
    }
}
