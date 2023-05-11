package ru.tinkoff.edu.java.scrapper.dto.request;

import java.net.URI;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

@Data
@AllArgsConstructor
public class LinkUpdateRequest {

    @NonNull
    private Long id;
    @NonNull
    private URI url;
    @NonNull
    private String description;
    @NonNull
    private ArrayList<Long> tgChatIds;

    public LinkUpdateRequest(Link link, String description) {
        this.id = link.getId();
        this.url = link.getUrl();
        this.description = description;
        this.tgChatIds = new ArrayList<>();
        this.tgChatIds.add(link.getChat());
    }

}
