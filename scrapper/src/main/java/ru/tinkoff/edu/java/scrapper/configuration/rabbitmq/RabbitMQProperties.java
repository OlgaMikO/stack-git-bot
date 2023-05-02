package ru.tinkoff.edu.java.scrapper.configuration.rabbitmq;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq")
public record RabbitMQProperties(@NotNull String exchangeName,
                                 @NotNull String queueName,
                                 @NotNull String routingKey) {
}
