package com.example.learntocode.services.chat;

import com.example.learntocode.mapper.DirectMessageMapper;
import com.example.learntocode.models.DirectMessage;
import com.example.learntocode.models.DirectMessageDto;
import com.example.learntocode.repository.DirectMessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final DirectMessageMapper directMessageMapper;
    private final DirectMessageRepository directMessageRepository;

    public ResponseEntity<?> createDirectMessage(DirectMessageDto data) {
        //todo: validate data
        DirectMessage directMessage = directMessageMapper.toEntity(data);

        directMessageRepository.save(directMessage);

        return ResponseEntity.ok("Message sent successfully");
    }

    public ResponseEntity<?> getDirectMessageById(Long id) {
        return ResponseEntity.ok(directMessageMapper.toDto(findById(directMessageRepository, id)));
    }

    public ResponseEntity<?> getChatHistory(Long senderId, Long receiverId) {
        List<DirectMessage> directMessages = directMessageRepository.findAllBySenderIdAndReceiverId(senderId, receiverId)
                .orElse(List.of());

        List<DirectMessageDto> directMessageDtos = directMessageMapper.toDto(directMessages);

        return ResponseEntity.ok(directMessageDtos);
    }

    public ResponseEntity<?> updateDirectMessage(Long id, DirectMessageDto data) {
        DirectMessage directMessage = findById(directMessageRepository, id);

        directMessage.setContent(data.getContent());
        directMessage.setEdited(true);

        directMessageRepository.save(directMessage);
        return ResponseEntity.ok("Message updated successfully");
    }

    public ResponseEntity<?> deleteDirectMessage(Long id) {
        directMessageRepository.deleteById(id);
        return ResponseEntity.ok("Message deleted successfully");
    }
}
