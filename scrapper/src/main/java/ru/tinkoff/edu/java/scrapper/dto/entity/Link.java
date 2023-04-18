package ru.tinkoff.edu.java.scrapper.dto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
public class Link {

    private Long id;

    private URI url;

    private Long chat;

    public Link(URI url, Long chat){
        this.url = url;
        this.chat = chat;
    }
}
