package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;

@Service
@RabbitListener(queues = "${bot.rabbitmq.queue-name}")
@RequiredArgsConstructor
public class ScrapperQueueListener {

    private final LinkUpdater linkUpdater;

    @RabbitHandler
    public void receiver(LinkUpdateRequest update) {
        linkUpdater.sendMessage(update);
    }
}
