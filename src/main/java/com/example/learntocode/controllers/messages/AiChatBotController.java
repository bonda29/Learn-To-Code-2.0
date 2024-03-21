package com.example.learntocode.controllers.messages;

import com.example.learntocode.payload.DTOs.ChatMemoDto;
import com.example.learntocode.services.auth0.UserClient;
import com.example.learntocode.services.openApi.AiChatBotService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openai")
public class AiChatBotController {

    private final AiChatBotService aiChatBotService;
    private final UserClient userClient;

    @PostMapping("/chat")
    public Map<String, ChatMemoDto> getChatResponse(@RequestBody ChatMemoDto data, HttpServletRequest request) {
        return aiChatBotService.createChatMemo(data);
    }

    @GetMapping("/user/history/{userId}")
    public ResponseEntity<List<ChatMemoDto>> getFirstMessageOfEachSessionByUserId(@PathVariable Long userId) {
        return aiChatBotService.getFirstMessageOfEachSessionByUserId(userId);
    }

}
