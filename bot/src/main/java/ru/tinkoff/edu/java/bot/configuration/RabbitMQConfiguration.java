package ru.tinkoff.edu.java.bot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final RabbitMQProperties rabbitMQProperties;

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
    public DirectExchange directExchange() {
        return new DirectExchange(rabbitMQProperties.exchangeName(), true, false);
    }

    @Bean
    public DirectExchange directExchangeDlq() {
        return new DirectExchange(rabbitMQProperties.exchangeName() + ".dlq", true, false);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(rabbitMQProperties.queueName()).
                withArgument("x-dead-letter-exchange", rabbitMQProperties.exchangeName() + ".dlx")
                .build();
    }

    @Bean
    public Queue queueDlq() {
        return new Queue(rabbitMQProperties.queueName() + ".dlq");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(rabbitMQProperties.routingKey());
    }

    @Bean
    public Binding bindingDlq() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(rabbitMQProperties.routingKey() + ".dlq");
    }

    @Bean
    public String exchangeName() {
        return rabbitMQProperties.exchangeName();
    }

    @Bean
    public String routingKey() {
        return rabbitMQProperties.routingKey();
    }

    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest", LinkUpdateRequest.class);
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.scrapper.dto.*");
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
