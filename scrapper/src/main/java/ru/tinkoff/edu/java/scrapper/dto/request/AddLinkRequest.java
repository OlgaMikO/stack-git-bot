package ru.tinkoff.edu.java.scrapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AddLinkRequest {

    @NonNull
    private String link;

}
