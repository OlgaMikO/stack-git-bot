package ru.tinkoff.edu.java.scrapper.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Link {

    private Long id;

    private String url;

    private Long chat;

    public Link(String url, Long chat){
        this.url = url;
        this.chat = chat;
    }
}
