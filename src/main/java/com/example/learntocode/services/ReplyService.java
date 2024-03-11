package com.example.learntocode.services;

import com.example.learntocode.mapper.ReplyMapper;
import com.example.learntocode.models.Reply;
import com.example.learntocode.payload.DTOs.ReplyDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.repository.ReplyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Service
@Transactional
public class ReplyService extends ReplyCRUD {

    private final ReplyRepository replyRepository;
    private final ReplyMapper replyMapper;

    public ReplyService(ReplyRepository replyRepository, ReplyMapper replyMapper) {
        super(replyRepository, replyMapper);
        this.replyRepository = replyRepository;
        this.replyMapper = replyMapper;
    }

    public ResponseEntity<MessageResponse> createChildReply(Long parentId, ReplyDto data) {
        validateReplyDto(data);

        Reply child = replyMapper.toEntity(data);
        Reply parent = findById(replyRepository, parentId);

        child.setParentReply(parent);

        replyRepository.save(child);

        return ResponseEntity.ok(new MessageResponse("The reply has been created successfully!"));
    }

    public ResponseEntity<?> getAllRepliesByQuestionId(Long questionId) {
        Optional<List<Reply>> optionalReplies = replyRepository.findAllByQuestionId(questionId);
        if (optionalReplies.isEmpty()) {
            return ResponseEntity.badRequest().body(MessageResponse.from("No replies found for the question with id: " + questionId));
        }

        List<ReplyDto> replyDtos = replyMapper.toDto(optionalReplies.get());

        // Create a map to store replies by their IDs
        Map<Long, ReplyDto> replyMap = new HashMap<>();
        replyDtos.forEach(replyDto -> replyMap.put(replyDto.getId(), replyDto));

        // Build a hierarchical structure
        List<ReplyDto> rootReplies = buildReplyTree(replyDtos, replyMap);

        rootReplies.sort(Comparator.comparing(ReplyDto::getDateOfCreation));
        sortChildReplies(rootReplies);

        return ResponseEntity.ok(rootReplies);
    }

    private List<ReplyDto> buildReplyTree(List<ReplyDto> replyDtos, Map<Long, ReplyDto> replyMap) {
        List<ReplyDto> rootReplies = new ArrayList<>();
        for (ReplyDto replyDto : replyDtos) {
            Long parentReplyId = replyDto.getParentReplyId();
            if (parentReplyId == null) {
                rootReplies.add(replyDto);
            } else {
                ReplyDto parentReply = replyMap.get(parentReplyId);
                if (parentReply != null) {
                    parentReply.getChildReplies().add(replyDto);
                }
            }
        }
        return rootReplies;
    }

    private void sortChildReplies(List<ReplyDto> replies) {
        for (ReplyDto reply : replies) {
            List<ReplyDto> childReplies = new ArrayList<>(reply.getChildReplies());
            childReplies.sort(Comparator.comparing(ReplyDto::getDateOfCreation));
            reply.setChildReplies(new HashSet<>(childReplies));
            sortChildReplies(childReplies);
        }
    }
}

