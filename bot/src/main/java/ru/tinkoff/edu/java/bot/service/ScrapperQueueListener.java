package ru.tinkoff.edu.java.bot.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;

@RabbitListener(queues = "${bot.rabbitmq.queue-name}")
public class ScrapperQueueListener {


    @RabbitHandler
    public void receiver(LinkUpdateRequest update) {

    }
}
