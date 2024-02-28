package com.example.learntocode.controllers;

import com.example.learntocode.payload.DTOs.ChatMemoDto;
import com.example.learntocode.services.openApi.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openai")
public class ChatBotController {

    private final ChatBotService chatBotService;


    @PostMapping("/chat")
    public Map<String, ChatMemoDto> getChatResponse(@RequestBody ChatMemoDto data) {
        return chatBotService.createChatMemo(data);
    }
}
