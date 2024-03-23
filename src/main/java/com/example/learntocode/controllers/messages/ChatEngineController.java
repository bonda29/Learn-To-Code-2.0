package com.example.learntocode.controllers.messages;

import com.example.learntocode.services.chatEngine.ChatEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat-engine")
public class ChatEngineController {
    private final ChatEngineService chatEngineService;

    @PutMapping("/users/{userId}")
    public ResponseEntity<?> getOrCreateUsers(@PathVariable Long userId) {
        return chatEngineService.getOrCreateUsers(userId);
    }

    @PutMapping("/chats/{userId}")
    public ResponseEntity<?> getOrCreateChats(@PathVariable Long userId, @RequestBody Set<Long> participants) {
        return chatEngineService.getOrCreateChat(userId, participants);
    }

}
