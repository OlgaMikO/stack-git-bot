package ru.tinkoff.edu.java.bot.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageCount {

    private final String metricName = "my_message_count";
    private final Counter counter;

    @Autowired
    public MessageCount(MeterRegistry registry) {
        counter = registry.counter(metricName);
    }

    public void incrementCounter() {
        counter.increment();
    }
}
