package com.example.learntocode.services.messages;

import com.example.learntocode.payload.DTOs.DirectMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;

    public void sendMessage(DirectMessageDto message) {
        String queueName = createQueue(message);

        rabbitTemplate.convertAndSend("directExchange", queueName, message);
    }

    private String createQueue(DirectMessageDto message) {
        String queueName = "Queue: " + message.getReceiverId();
        return amqpAdmin.declareQueue(new Queue(queueName, false, false, false));
    }
}