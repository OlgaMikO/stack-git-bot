package ru.tinkoff.edu.java.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class LinkUpdateRequest {

    @NonNull
    private Long id;
    @NonNull
    private String url;
    @NonNull
    private String description;
    @NonNull
    private ArrayList<Long> tgChatIds;

}
