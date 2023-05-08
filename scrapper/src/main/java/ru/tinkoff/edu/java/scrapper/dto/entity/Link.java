package ru.tinkoff.edu.java.scrapper.dto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@NoArgsConstructor
public class Link {

    private Long id;

    private URI url;

    private Long chat;

    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastActivity;

    private Integer answerCount;

    private Integer commentCount;

    public Link(URI url, Long chat) {
        this.url = url;
        this.chat = chat;
        this.lastUpdate = OffsetDateTime.now();
        this.lastActivity = OffsetDateTime.now();
    }

}
