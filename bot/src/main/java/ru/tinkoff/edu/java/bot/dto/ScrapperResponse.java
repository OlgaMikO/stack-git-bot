package ru.tinkoff.edu.java.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class ScrapperResponse {

    @NotNull
    @JsonProperty(value = "body")
    private String responseBody;
}
