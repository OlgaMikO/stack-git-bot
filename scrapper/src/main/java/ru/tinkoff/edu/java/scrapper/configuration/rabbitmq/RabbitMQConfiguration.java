package ru.tinkoff.edu.java.scrapper.configuration.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.IBotClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.configuration.client.ClientConfigProperties;
import ru.tinkoff.edu.java.scrapper.service.rabbitmq.QueueLinkUpdate;
import ru.tinkoff.edu.java.scrapper.service.rabbitmq.ScrapperQueueProducer;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final RabbitMQProperties rabbitMQProperties;

    private final ClientConfigProperties clientProperties;

    private final ApplicationConfig config;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(rabbitMQProperties.exchangeName(), true, false);
    }

    @Bean
    public Queue queue(){
        return new Queue(rabbitMQProperties.queueName());
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(rabbitMQProperties.routingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public String exchangeName(){
        return rabbitMQProperties.exchangeName();
    }

    @Bean
    public String routingKey(){
        return rabbitMQProperties.routingKey();
    }
    @Bean
    public ScrapperQueueProducer scrapperQueueProducer(){
        return new ScrapperQueueProducer(rabbitTemplate(), exchangeName(), routingKey());
    }

    @Bean
    public IBotClient botClient() {
        if(config.useQueue()){
            return new QueueLinkUpdate(scrapperQueueProducer());
        }
        return new BotClient(clientProperties.botClientBaseUrl());
    }
}
