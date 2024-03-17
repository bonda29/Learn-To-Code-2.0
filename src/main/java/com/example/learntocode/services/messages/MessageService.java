package com.example.learntocode.services.messages;

import com.example.learntocode.mapper.DirectMessageMapper;
import com.example.learntocode.models.DirectMessage;
import com.example.learntocode.payload.DTOs.DirectMessageDto;
import com.example.learntocode.payload.messages.MessageResponse;
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
public class MessageService {
    private final DirectMessageMapper directMessageMapper;
    private final DirectMessageRepository directMessageRepository;
    private final MessageSender messageSender;
    private final MessageReceiver messageReceiver;

    public ResponseEntity<MessageResponse> createDirectMessage(DirectMessageDto data) {
        //todo: validate data
        DirectMessage directMessage = directMessageMapper.toEntity(data);

        directMessageRepository.save(directMessage);
        messageSender.sendDirectMessage(directMessageMapper.toDto(directMessage));

        return ResponseEntity.ok(MessageResponse.from("Message sent successfully"));
    }

    public ResponseEntity<DirectMessageDto> getDirectMessageById(Long id) {
        return ResponseEntity.ok(directMessageMapper.toDto(findById(directMessageRepository, id)));
    }

    public ResponseEntity<List<DirectMessageDto>> getChatHistory(Long senderId, Long receiverId) {
        List<DirectMessage> directMessages = directMessageRepository.findAllBySenderIdAndReceiverId(senderId, receiverId)
                .orElse(List.of());

        List<DirectMessageDto> directMessageDtos = directMessageMapper.toDto(directMessages);

        return ResponseEntity.ok(directMessageDtos);
    }

    public ResponseEntity<MessageResponse> updateDirectMessage(Long id, DirectMessageDto data) {
        DirectMessage directMessage = findById(directMessageRepository, id);

        directMessage.setContent(data.getContent());
        directMessage.setEdited(true);

        directMessageRepository.save(directMessage);
        return ResponseEntity.ok(MessageResponse.from("Message updated successfully"));
    }

    public ResponseEntity<MessageResponse> deleteDirectMessage(Long id) {
        directMessageRepository.deleteById(id);
        return ResponseEntity.ok(MessageResponse.from("Message deleted successfully"));
    }
}
