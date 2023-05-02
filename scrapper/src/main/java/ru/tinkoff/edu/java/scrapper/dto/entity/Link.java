package ru.tinkoff.edu.java.scrapper.dto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.AllLinkParser;

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

    private Integer answerCount;

    private Integer commentCount;

    private final AllLinkParser parser = new AllLinkParser();

    public Link(URI url, Long chat){
        this.url = url;
        this.chat = chat;
        this.lastUpdate = OffsetDateTime.now();
    }
}
