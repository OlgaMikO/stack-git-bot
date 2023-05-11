package ru.tinkoff.edu.java;

import ru.tinkoff.edu.java.parser.GitHubParser;
import ru.tinkoff.edu.java.parser.LinkParser;
import ru.tinkoff.edu.java.parser.NullParser;
import ru.tinkoff.edu.java.parser.StackOverflowParser;

public class AllLinkParser {

    private LinkParser linkParser;

    public AllLinkParser() {
        LinkParser nullParser = new NullParser(null);
        LinkParser githubParser = new GitHubParser(nullParser);
        linkParser = new StackOverflowParser(githubParser);
    }

    public LinkParser getLinkParser() {
        return linkParser;
    }

}
