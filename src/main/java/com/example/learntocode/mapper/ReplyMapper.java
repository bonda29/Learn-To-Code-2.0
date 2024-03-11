package com.example.learntocode.mapper;


import com.example.learntocode.models.Reply;
import com.example.learntocode.payload.DTOs.ReplyDto;
import com.example.learntocode.repository.QuestionRepository;
import com.example.learntocode.repository.ReplyRepository;
import com.example.learntocode.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Component
public class ReplyMapper {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ReplyRepository replyRepository;

    public ReplyMapper(ModelMapper modelMapper, UserRepository userRepository, QuestionRepository questionRepository, ReplyRepository replyRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.replyRepository = replyRepository;

//        this.modelMapper.addMappings(new PropertyMap<Reply, ReplyDto>() {
//            @Override
//            protected void configure() {
//                map().setAuthorId(source.getAuthor().getId());
//                map().setQuestionId(source.getQuestion().getId());
//                map().setParentReplyId(source.getParentReply() == null ? null : source.getParentReply().getId());
//
//            }
//        });
    }

    public ReplyDto toDto(Reply reply) {
        ReplyDto dto = modelMapper.map(reply, ReplyDto.class);

        dto.setAuthorId(reply.getAuthor().getId());
        dto.setQuestionId(reply.getQuestion().getId());

        if (reply.getParentReply() != null) {
            dto.setParentReplyId(reply.getParentReply().getId());
        }

        if (reply.getChildReplies() != null) {
            dto.setChildReplies(reply.getChildReplies().stream()
                    .map(this::toDto)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    public List<ReplyDto> toDto(List<Reply> replies) {
        return replies.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Reply toEntity(ReplyDto replyDto) {
        Reply reply = modelMapper.map(replyDto, Reply.class);
        reply.setAuthor(findById(userRepository, replyDto.getAuthorId()));
        reply.setQuestion(findById(questionRepository, replyDto.getQuestionId()));

        if (replyDto.getParentReplyId() != null) {
            reply.setParentReply(findById(replyRepository, replyDto.getParentReplyId()));
        }

        if (replyDto.getChildReplies() != null) {
            reply.setChildReplies(replyDto.getChildReplies().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toSet()));
        }

        return reply;
    }
}
