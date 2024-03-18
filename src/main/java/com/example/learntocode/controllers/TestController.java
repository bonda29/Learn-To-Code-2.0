package com.example.learntocode.controllers;

import com.example.learntocode.models.User;
import com.example.learntocode.payload.DTOs.DirectMessageDto;
import com.example.learntocode.services.auth0.Auth0Client;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{receiverId}")
    public void sendMessage(@DestinationVariable String receiverId, DirectMessageDto message) {
        // Send the message to the recipient
        messagingTemplate.convertAndSendToUser(receiverId, "/queue/messages", message);
    }

}
