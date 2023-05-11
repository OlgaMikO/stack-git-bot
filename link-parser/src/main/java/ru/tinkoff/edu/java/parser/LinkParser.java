package ru.tinkoff.edu.java.parser;

import java.net.URI;
import ru.tinkoff.edu.java.answer.ParserAnswer;

public abstract class LinkParser {
    private LinkParser next;

    public LinkParser(LinkParser next) {
        this.next = next;
    }

    public LinkParser getNext() {
        return next;
    }

    public abstract ParserAnswer parseLink(URI link);

    public abstract boolean matchLink(URI link);
}
