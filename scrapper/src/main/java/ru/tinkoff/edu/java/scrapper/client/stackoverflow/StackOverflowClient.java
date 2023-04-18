package ru.tinkoff.edu.java.scrapper.client.stackoverflow;

import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;

public interface StackOverflowClient {

    StackOverflowResponse fetchQuestion(Long id);
}
