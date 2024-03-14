package com.example.learntocode.services.messages;

import com.example.learntocode.payload.DTOs.DirectMessageDto;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageReceiver {

    private final ConnectionFactory connectionFactory;
    private final Map<String, SimpleMessageListenerContainer> listenerContainers = new HashMap<>();

    public void startListening(String userId) {
        String queueName = "Queue: " + userId;

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(new MessageListenerAdapter(this, "receiveMessage"));
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // Set acknowledge mode to MANUAL
        container.start();

        listenerContainers.put(userId, container);
    }

    public void stopListening(String userId) {
        SimpleMessageListenerContainer container = listenerContainers.get(userId);
        if (container != null) {
            container.stop();
            listenerContainers.remove(userId);
        }
    }

    @RabbitListener(queues = "Queue: 1")
    public void receiveMessage(DirectMessageDto message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("Received message from " + message.getSenderId() + ": " + message.getContent());
        channel.basicAck(tag, false); // Acknowledge the message
    }
}