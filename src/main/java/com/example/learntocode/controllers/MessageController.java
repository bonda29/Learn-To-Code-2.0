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
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody DirectMessageDto message) {
        return messageService.createDirectMessage(message);
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

    @GetMapping("/start-listening")
    public ResponseEntity<MessageResponse> startListening(@RequestParam String userId) {
        messageService.startListening(userId);
        return ResponseEntity.ok(MessageResponse.from("Listening started"));
    }
}
