//package com.example.learntocode.controllers.messages;
//
//import com.example.learntocode.models.chat.ChatNotification;
//import com.example.learntocode.payload.DTOs.ChatMessageDto;
//import com.example.learntocode.services.messages.ChatMessageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@RequiredArgsConstructor
//public class ChatWebsocketController {
//    private final SimpMessagingTemplate messagingTemplate;
//    private final ChatMessageService chatMessageService;
//
//    @MessageMapping("/chat")
//    public void processMessage(@Payload ChatMessageDto chatMessage) {
//        ChatMessageDto savedMsg = chatMessageService.createChatMessage(chatMessage);
//        messagingTemplate.convertAndSendToUser(
//                String.valueOf(chatMessage.getRecipientId()), "/queue/messages",
//                new ChatNotification(
//                        savedMsg.getId(),
//                        savedMsg.getSenderId(),
//                        savedMsg.getRecipientId(),
//                        savedMsg.getContent()
//                )
//        );
//    }
//}
