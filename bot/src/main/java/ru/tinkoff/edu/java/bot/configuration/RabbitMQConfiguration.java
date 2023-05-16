package ru.tinkoff.edu.java.bot.configuration;

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
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final ApplicationConfig rabbitMQProperties;
    private static final String USERNAME = "guest";

    private static final String DLQ = ".dlq";

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
        return new DirectExchange(rabbitMQProperties.exchangeName(), true, false);
    }

    @Bean
    public DirectExchange directExchangeDlq() {
        return new DirectExchange(rabbitMQProperties.exchangeName() + DLQ, true, false);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(rabbitMQProperties.queueName())
            .withArgument("x-dead-letter-exchange", rabbitMQProperties.exchangeName() + DLQ)
            .build();
    }

    @Bean
    public Queue queueDlq() {
        return new Queue(rabbitMQProperties.queueName() + DLQ);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(rabbitMQProperties.routingKey());
    }

    @Bean
    public Binding bindingDlq() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(rabbitMQProperties.routingKey() + DLQ);
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
