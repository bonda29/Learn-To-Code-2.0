package com.example.learntocode.controllers;

import com.example.learntocode.services.openApi.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openai")
public class ChatBotController {

    private final ChatService chatService;

    @GetMapping("/")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(chatService.test());
    }
}
