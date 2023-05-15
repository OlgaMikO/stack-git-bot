package ru.tinkoff.edu.java.scrapper.service.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.client.IBotClient;
import ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.BotResponse;

@Service
public class QueueLinkUpdate implements IBotClient {
    private final ScrapperQueueProducer producer;

    @Autowired
    public QueueLinkUpdate(@Qualifier("getScrapperQueueProducer") ScrapperQueueProducer producer) {
        this.producer = producer;
    }

    @Override
    public BotResponse linkUpdate(LinkUpdateRequest request) {
        producer.send(request);
        return null;
    }
}
