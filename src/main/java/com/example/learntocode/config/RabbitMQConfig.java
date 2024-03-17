package com.example.learntocode.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String directMessagesExchange = "direct_messages_exchange";
    public static final String directMessagesQueue = "direct_messages_queue";
    public static final String directMessagesRoutingKey = "direct_messages_routing_key";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directMessagesExchange);
    }

    @Bean
    public Queue directMessageQueue() {
        return new Queue(directMessagesQueue, true);
    }

    @Bean
    public Binding binding(DirectExchange directExchange, Queue directMessageQueue) {
        return BindingBuilder.bind(directMessageQueue).to(directExchange).with(directMessagesRoutingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

}