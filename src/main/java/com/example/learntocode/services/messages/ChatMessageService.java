//package com.example.learntocode.services.messages;
//
//import com.example.learntocode.errors.ResourceNotFoundException;
//import com.example.learntocode.mapper.ChatMessageMapper;
//import com.example.learntocode.models.chat.ChatMessage;
//import com.example.learntocode.payload.DTOs.ChatMessageDto;
//import com.example.learntocode.repository.ChatMessageRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ChatMessageService {
//    private final ChatMessageRepository chatMessageRepository;
//    private final ChatRoomService chatRoomService;
//    private final ChatMessageMapper chatMessageMapper;
//
//    public ChatMessageDto createChatMessage(ChatMessageDto data) {
//        var chatRoomId = chatRoomService
//                .getChatRoomId(data.getSenderId(), data.getRecipientId(), true);
//        data.setChatRoomId(chatRoomId);
//
//        var chatMessage = chatMessageMapper.toEntity(data);
//
//        return chatMessageMapper.toDto(chatMessageRepository.save(chatMessage));
//    }
//
//    public ResponseEntity<List<ChatMessageDto>> findChatMessages(Long senderId, Long recipientId) {
//        Long chatRoomId = chatRoomService.getChatRoomId(senderId, recipientId, false);
//
//        if (chatRoomId == null) {
//            throw new ResourceNotFoundException("Chat room with senderId: " + senderId + " and recipientId: " + recipientId + " not found");
//        }
//
//        List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomIdOrderByTimestamp(chatRoomId).orElse(new ArrayList<>());
//
//        return ResponseEntity.ok(chatMessageMapper.toDto(chatMessages));
//    }
//}
