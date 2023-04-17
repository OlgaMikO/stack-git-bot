package ru.tinkoff.edu.java.scrapper.domain;

import ru.tinkoff.edu.java.scrapper.dto.Link;

import java.util.List;

public abstract class LinkDao {

    public abstract long add(Link link);

    public abstract int remove(Long id);

    public abstract List<Link> findAll();
}
