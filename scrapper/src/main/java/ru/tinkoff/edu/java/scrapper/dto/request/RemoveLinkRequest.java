package ru.tinkoff.edu.java.scrapper.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

public record RemoveLinkRequest(@NonNull @JsonProperty("link") String link) {

}
