package com.example.learntocode.controllers.messages;

import com.example.learntocode.payload.DTOs.ChatMessageDto;
import com.example.learntocode.services.messages.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final ChatMessageService chatMessageService;

    @GetMapping("/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessageDto>> findChatMessages(@PathVariable Long senderId, @PathVariable Long recipientId) {
        return chatMessageService.findChatMessages(senderId, recipientId);
    }

}
