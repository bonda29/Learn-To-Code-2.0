package com.example.learntocode.services.messages;

import com.example.learntocode.config.RabbitMQConfig;
import com.example.learntocode.mapper.DirectMessageMapper;
import com.example.learntocode.payload.DTOs.DirectMessageDto;
import com.example.learntocode.repository.DirectMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final DirectMessageMapper directMessageMapper;
    private final DirectMessageRepository directMessageRepository;

    public void sendDirectMessage(DirectMessageDto message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.directMessagesExchange, RabbitMQConfig.directMessagesRoutingKey, message);
    }
}