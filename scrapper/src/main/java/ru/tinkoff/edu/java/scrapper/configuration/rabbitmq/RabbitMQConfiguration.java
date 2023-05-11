package ru.tinkoff.edu.java.scrapper.configuration.rabbitmq;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.IBotClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.configuration.client.ClientConfigProperties;
import ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.service.rabbitmq.QueueLinkUpdate;
import ru.tinkoff.edu.java.scrapper.service.rabbitmq.ScrapperQueueProducer;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final ClientConfigProperties clientProperties;

    private final ApplicationConfig config;

    private static final String USERNAME = "guest";

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername(USERNAME);
        cachingConnectionFactory.setPassword(USERNAME);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(config.exchangeName(), true, false);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(config.queueName())
                .withArgument("x-dead-letter-exchange", config.exchangeName() + ".dlx")
                .build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(config.routingKey());
    }

    @Bean
    public String exchangeName() {
        return config.exchangeName();
    }

    @Bean
    public String routingKey() {
        return config.routingKey();
    }

    @Bean
    public ScrapperQueueProducer getScrapperQueueProducer() {
        return new ScrapperQueueProducer(rabbitTemplate(), exchangeName(), routingKey());
    }

    @Bean
    public IBotClient botClient() {
        if (config.useQueue()) {
            return new QueueLinkUpdate(getScrapperQueueProducer());
        }
        return new BotClient(clientProperties.botClientBaseUrl());
    }

    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest", LinkUpdateRequest.class);
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.bot.dto.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }
}
