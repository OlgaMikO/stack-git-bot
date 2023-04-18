package ru.tinkoff.edu.java.scrapper.domain;

import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public abstract class LinkDao {

    public abstract long add(Link link);

    public abstract int remove(Long id);

    public abstract List<Link> findAll();

    public abstract List<Link> findOldLinks();

    public abstract int update(Long id, OffsetDateTime time, Integer answerCount, Integer commentCount);

    public abstract Link find(URI url, Long chatId);
}
