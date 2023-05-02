package ru.tinkoff.edu.java.scrapper.service.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.client.IBotClient;
import ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.BotResponse;

@RequiredArgsConstructor
@Service
public class QueueLinkUpdate implements IBotClient {

    private final ScrapperQueueProducer producer;
    @Override
    public BotResponse linkUpdate(LinkUpdateRequest request) {
        producer.send(request);
        return null;
    }
}
