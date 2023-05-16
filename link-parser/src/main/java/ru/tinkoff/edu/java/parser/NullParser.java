package ru.tinkoff.edu.java.parser;

import java.net.URI;
import ru.tinkoff.edu.java.answer.ParserAnswer;

public class NullParser extends LinkParser {

    public NullParser(LinkParser next) {
        super(next);
    }

    public ParserAnswer parseLink(URI link) {
        return null;
    }

    public boolean matchLink(URI link) {
        return false;
    }
}
