package com.example.learntocode.controllers;

import com.example.learntocode.payload.DTOs.DirectMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public DirectMessageDto sendMessage(@Payload DirectMessageDto chatMessage) {
        // Process sending message here (e.g., save to the database, send via RabbitMQ)
        return chatMessage;
    }

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/public")
    public DirectMessageDto newUser(@Payload DirectMessageDto chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add the new user in the session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderId());
        return chatMessage;
    }
}