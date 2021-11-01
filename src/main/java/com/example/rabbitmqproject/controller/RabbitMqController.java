package com.example.rabbitmqproject.controller;

import com.example.rabbitmqproject.mq.RabbitMqService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RabbitMqController {

    @Resource
    private RabbitMqService rabbitMqService;

    @GetMapping("/test")
    public void test() {
        rabbitMqService.sendMessage("hello message!");
    }
}
