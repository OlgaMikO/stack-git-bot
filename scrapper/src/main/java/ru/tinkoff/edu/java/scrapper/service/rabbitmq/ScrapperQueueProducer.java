package ru.tinkoff.edu.java.scrapper.service.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {

    private final RabbitTemplate rabbitTemplate;

    private final String exchangeName;

    private final String routingKey;

    public void send(LinkUpdateRequest update) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, update);
    }
}
