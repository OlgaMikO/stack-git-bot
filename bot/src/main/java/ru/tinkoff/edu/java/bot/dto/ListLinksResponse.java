package ru.tinkoff.edu.java.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ListLinksResponse {

    @NotNull
    @JsonProperty(value = "links")
    private ArrayList<LinkResponse> links;

    @NotNull
    @JsonProperty(value = "size")
    private Long size;
}
