package com.example.learntocode.controllers;

import com.example.learntocode.payload.DTOs.DirectMessageDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.services.messages.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;


    @PostMapping("/")
    public ResponseEntity<?> sendMessage(@RequestBody DirectMessageDto message) {
        messageService.createDirectMessage(message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectMessageDto> getMessage(@PathVariable Long id) {
        return messageService.getDirectMessageById(id);
    }

    @GetMapping("/history")
    public ResponseEntity<List<DirectMessageDto>> getChatHistory(@RequestParam Long senderId, @RequestParam Long receiverId) {
        return messageService.getChatHistory(senderId, receiverId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateMessage(@PathVariable Long id, @RequestBody DirectMessageDto message) {
        return messageService.updateDirectMessage(id, message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteMessage(@PathVariable Long id) {
        return messageService.deleteDirectMessage(id);
    }
}
