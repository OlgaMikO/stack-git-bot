package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class StackOverflowResponse {

    @NotNull
    @JsonProperty(value = "last_activity_date")
    private OffsetDateTime activity;

    @NotNull
    @JsonProperty(value = "creation_date")
    private OffsetDateTime creation;

    @NotNull
    @JsonProperty(value = "question_id")
    private Long id;

    @NotNull
    @JsonProperty(value = "comment_count")
    private Integer commentCount;

    @NotNull
    @JsonProperty(value = "answer_count")
    private Integer answerCount;
}
