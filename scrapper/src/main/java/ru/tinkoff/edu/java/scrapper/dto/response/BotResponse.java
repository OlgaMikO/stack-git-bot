package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class BotResponse {

    @NotNull
    @JsonProperty(value = "body")
    private String responseBody;

}
