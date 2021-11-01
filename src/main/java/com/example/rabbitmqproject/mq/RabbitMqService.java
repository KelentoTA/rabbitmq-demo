package com.example.rabbitmqproject.mq;

public interface RabbitMqService {

    /**
     * 普通消息
     */
    void sendMessage(Object message);

    /**
     * 延时消息
     * @param message 消息
     * @param delayTime 延时时间 （1000L为 1秒）
     */
    void sendDelayMessage(Object message, Long delayTime);
}
