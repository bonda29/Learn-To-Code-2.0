package com.example.learntocode.controllers;

import com.example.learntocode.models.User;
import com.example.learntocode.payload.DTOs.ChatMemoDto;
import com.example.learntocode.services.auth0.UserClient;
import com.example.learntocode.services.openApi.AiChatBotService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.learntocode.util.TokenUtil.extractToken;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openai")
public class AiChatBotController {

    private final AiChatBotService aiChatBotService;
    private final UserClient userClient;

    @PostMapping("/chat")
    public Map<String, ChatMemoDto> getChatResponse(@RequestBody ChatMemoDto data, HttpServletRequest request) {
//        User user = userClient.getUserByAccessToken(extractToken(request));
////        System.out.println(user.getEmail());

        return aiChatBotService.createChatMemo(data);
    }
}
