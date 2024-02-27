package com.example.learntocode.services;

import com.example.learntocode.mapper.ReplyMapper;
import com.example.learntocode.models.Reply;
import com.example.learntocode.payload.DTOs.ReplyDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.repository.QuestionRepository;
import com.example.learntocode.repository.ReplyRepository;
import com.example.learntocode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final ReplyMapper replyMapper;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public ResponseEntity<MessageResponse> createReply(ReplyDto data) {
        //todo: validate the data

        Reply reply = replyMapper.toEntity(data);
        replyRepository.save(reply);

        return ResponseEntity.ok(MessageResponse.from("The reply has been created successfully!"));
    }

    public ResponseEntity<MessageResponse> createChildReply(Long parentId, ReplyDto data) {
        //todo: validate the data

        Reply child = replyMapper.toEntity(data);
        Reply parent = findById(replyRepository, parentId);

        child.setParent(parent);

        replyRepository.save(child);

        return ResponseEntity.ok(new MessageResponse("The reply has been created successfully!"));

    }

    public ResponseEntity<ReplyDto> getReplyById(Long id) {
        Reply reply = findById(replyRepository, id);
        ReplyDto replyDto = replyMapper.toDto(reply);

        return ResponseEntity.ok(replyDto);
    }

    public ResponseEntity<List<ReplyDto>> getAllReplies() {
        List<Reply> replies = replyRepository.findAll();
        List<ReplyDto> replyDtos = replyMapper.toDto(replies);

        return ResponseEntity.ok(replyDtos);
    }

    public ResponseEntity<MessageResponse> updateReply(Long id, String text) {
        Reply reply = findById(replyRepository, id);

        reply.setText(text);

        replyRepository.save(reply);

        return ResponseEntity.ok(MessageResponse.from("The reply has been changed successfully!"));
    }

    public ResponseEntity<MessageResponse> deleteReply(Long id) {
        replyRepository.deleteById(id);

        return ResponseEntity.ok(MessageResponse.from("The reply has been deleted successfully!"));
    }
}

