package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class GitHubResponse {

    @NotNull
    @JsonProperty(value = "id")
    private long id;

    @NotNull
    @JsonProperty(value = "full_name")
    private String fullName;

    @NotNull
    @JsonProperty(value = "created_at")
    private OffsetDateTime createdAt;

}
