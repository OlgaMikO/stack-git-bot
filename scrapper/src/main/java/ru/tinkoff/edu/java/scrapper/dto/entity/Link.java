package ru.tinkoff.edu.java.scrapper.dto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class Link {

    private Long id;

    private URI url;

    private Long chat;

    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastActivity;

    public Link(URI url, Long chat){
        this.url = url;
        this.chat = chat;
        this.lastActivity = OffsetDateTime.now();
        this.lastUpdate = OffsetDateTime.now();
    }
}
