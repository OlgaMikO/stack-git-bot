package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot.rabbitmq")
public record RabbitMQProperties(@NotNull String exchangeName,
                                 @NotNull String queueName,
                                 @NotNull String routingKey) {
}
