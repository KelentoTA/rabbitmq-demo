package com.example.rabbitmqproject.mq.listener;

import com.example.rabbitmqproject.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class RabbitMqListener {

    private int retryCount = 0;

    /**
     * 事件处理初始化
     */
    @RabbitListener(queues = RabbitMqConfig.NORMAL_QUEUE)
    public void receiveEventProcessInit(Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody());
        try {
            System.out.println("收到订单队列消息：" + msg);
//            int a = 1 / 0;
//            System.out.println("errorMsg");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            retryCount++;
            // 抛出异常触发重试机制
            throw e;
        } finally {
            // 达到重试次数限制，将消息发送到死信队列
            if (retryCount == 3) {
                System.out.println("-----11111------");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            }
        }
    }

    /**
     * 接收延时消息
     */
    @RabbitListener(queues = RabbitMqConfig.PROCESS_QUEUE)
    public void receiveDelayMessage(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("@receiveDelayMessage.message: " + msg);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.error("消息消费发生异常，error msg:{}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = RabbitMqConfig.DEAD_FOR_TASK_QUEUE)
    public void processMessage(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("@processMessage.message: " + msg);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.error("消息消费发生异常，error msg:{}", e.getMessage(), e);
        }
    }

}
