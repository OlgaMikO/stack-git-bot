package ru.tinkoff.edu.java.parser;

import java.net.URI;
import ru.tinkoff.edu.java.answer.GitHubAnswer;
import ru.tinkoff.edu.java.answer.ParserAnswer;

public class GitHubParser extends LinkParser {

    public GitHubParser(LinkParser next) {
        super(next);
    }

    public ParserAnswer parseLink(URI link) {
        if (matchLink(link)) {
            String[] path = link.getPath().split("/");
            return new GitHubAnswer(path[1], path[2]);
        } else {
            return this.getNext().parseLink(link);
        }
    }

    public boolean matchLink(URI link) {
        return link.getAuthority().equals("github.com");
    }

}
