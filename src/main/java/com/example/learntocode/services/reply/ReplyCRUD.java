package com.example.learntocode.services.reply;

import com.example.learntocode.errors.InvalidReplyException;
import com.example.learntocode.mapper.ReplyMapper;
import com.example.learntocode.models.Reply;
import com.example.learntocode.payload.DTOs.ReplyDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyCRUD {
    private final ReplyRepository replyRepository;
    private final ReplyMapper replyMapper;

    public ResponseEntity<MessageResponse> createReply(ReplyDto data) {
        validateReplyDto(data);

        Reply reply = replyMapper.toEntity(data);
        replyRepository.save(reply);

        return ResponseEntity.ok(MessageResponse.from("The reply has been created successfully!"));
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

    public void validateReplyDto(ReplyDto replyDto) {
        if (replyDto.getAuthorId() == null) {
            throw new InvalidReplyException("AuthorId cannot be null");
        }

        if (replyDto.getText() == null || replyDto.getText().isEmpty()) {
            throw new InvalidReplyException("Text cannot be null or empty");
        }

        if (replyDto.getQuestionId() == null) {
            throw new InvalidReplyException("QuestionId cannot be null");
        }
    }
}
