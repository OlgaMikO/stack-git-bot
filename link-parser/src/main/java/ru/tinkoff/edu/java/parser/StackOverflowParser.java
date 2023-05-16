package ru.tinkoff.edu.java.parser;

import java.net.URI;
import ru.tinkoff.edu.java.answer.ParserAnswer;
import ru.tinkoff.edu.java.answer.StackOverflowAnswer;

public class StackOverflowParser extends LinkParser {

    public StackOverflowParser(LinkParser next) {
        super(next);
    }

    public ParserAnswer parseLink(URI link) {
        if (matchLink(link)) {
            String[] path = link.getPath().split("/");
            return new StackOverflowAnswer(Long.parseLong(path[2]));
        } else {
            return this.getNext().parseLink(link);
        }
    }

    public boolean matchLink(URI link) {
        if (link.getAuthority() == null) {
            return false;
        }
        return link.getAuthority().equals("stackoverflow.com");
    }
}
