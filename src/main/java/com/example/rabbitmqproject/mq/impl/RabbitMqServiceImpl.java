package com.example.rabbitmqproject.mq.impl;

import com.example.rabbitmqproject.config.RabbitMqConfig;
import com.example.rabbitmqproject.mq.RabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class RabbitMqServiceImpl implements RabbitMqService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(Object message) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.NORMAL_QUEUE, message);
    }

    @Override
    public void sendDelayMessage(Object message, Long delayTime) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.DEAD_EXCHANGE, RabbitMqConfig.DEAD_ROUTING_KEY, message, msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(delayTime));
            return msg;
        });
    }

}
