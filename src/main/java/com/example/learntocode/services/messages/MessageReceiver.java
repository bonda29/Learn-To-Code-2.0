package com.example.learntocode.services.messages;

import com.example.learntocode.config.RabbitMQConfig;
import com.example.learntocode.models.DirectMessage;
import com.example.learntocode.payload.DTOs.DirectMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageReceiver {


    @RabbitListener(queues = RabbitMQConfig.directMessagesQueue)
    public void receiveDirectMessage(DirectMessageDto message) {
        System.out.println("Received message: " + message);
    }
}