package ru.tinkoff.edu.java.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LinkResponse {

    @NotNull
    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @JsonProperty(value = "url")
    private URI url;
}
