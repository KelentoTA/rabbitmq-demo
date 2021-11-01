package com.example.rabbitmqproject.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    // 死信队列
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_QUEUE = "dead_queue";
    public static final String DEAD_ROUTING_KEY = "dead_routing_key";

    // 执行队列
    public static final String PROCESS_EXCHANGE = "process_exchange";
    public static final String PROCESS_QUEUE = "process_queue";
    public static final String PROCESS_ROUTING_KEY = "process_routing_key";

    // 普通队列
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String NORMAL_ROUTING_KEY = "normal_routing_key";

    // 任务的死信队列
    public static final String DEAD_FOR_TASK_EXCHANGE = "dead_for_task_exchange";
    public static final String DEAD_FOR_TASK_QUEUE = "dead_for_task_queue";
    public static final String DEAD_FOR_TASK_ROUTING_KEY = "dead_for_task_routing_key";

    @Bean
    public Queue deadQueue() {
        return QueueBuilder.durable(DEAD_QUEUE)
                .withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", PROCESS_ROUTING_KEY)
                .build();
    }

    @Bean
    public DirectExchange deadExchange() {
        return ExchangeBuilder.directExchange(DEAD_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding bindDeadBuilders() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(DEAD_ROUTING_KEY);
    }

    @Bean
    public Queue deadForTaskQueue() {
        return QueueBuilder.durable(DEAD_FOR_TASK_QUEUE).build();
    }

    @Bean
    public DirectExchange deadForTaskExchange() {
        return ExchangeBuilder.directExchange(DEAD_FOR_TASK_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding bindDeadForTaskBuilders() {
        return BindingBuilder.bind(deadForTaskQueue()).to(deadForTaskExchange()).with(DEAD_FOR_TASK_ROUTING_KEY);
    }

    @Bean
    public Queue normalQueue() {
        return QueueBuilder.durable(NORMAL_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_FOR_TASK_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_FOR_TASK_ROUTING_KEY)
                .build();
    }

    @Bean
    public DirectExchange normalExchange() {
        return ExchangeBuilder.directExchange(NORMAL_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding bindNormalBuilders() {
        return BindingBuilder.bind(normalQueue()).to(normalExchange()).with(NORMAL_ROUTING_KEY);
    }

    @Bean
    public Queue processQueue() {
        return QueueBuilder.durable(PROCESS_QUEUE).build();
    }

    @Bean
    public DirectExchange processExchange() {
        return ExchangeBuilder.directExchange(PROCESS_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding bindProcessBuilders() {
        return BindingBuilder.bind(processQueue()).to(processExchange()).with(PROCESS_ROUTING_KEY);
    }

}
