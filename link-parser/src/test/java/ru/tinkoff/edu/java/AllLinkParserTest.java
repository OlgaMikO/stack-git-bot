package ru.tinkoff.edu.java;

import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.java.answer.GitHubAnswer;
import ru.tinkoff.edu.java.answer.ParserAnswer;
import ru.tinkoff.edu.java.answer.StackOverflowAnswer;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

public class AllLinkParserTest {

    private final AllLinkParser allLinkParser = new AllLinkParser();

    @Test
    public void githubParseLinkTest() {
        // GIVEN
        URI link = URI.create("https://github.com/OlgaMikO/stack-git-bot/tree/hw1");

        // WHEN
        ParserAnswer actual = allLinkParser.getLinkParser().parseLink(link);

        // THEN
        assertInstanceOf(GitHubAnswer.class, actual);
    }

    @Test
    public void stackoverflowParseLinkTest() {
        // GIVEN
        URI link = URI.create("https://stackoverflow.com/questions/62147477/java-records-stackoverflow-runtimeexception");

        // WHEN
        ParserAnswer actual = allLinkParser.getLinkParser().parseLink(link);

        // THEN
        assertInstanceOf(StackOverflowAnswer.class, actual);
    }

    @Test
    public void unknownParseLinkTest() {
        // GIVEN
        URI link = URI.create("https://www.youtube.com/watch?v=URQOHdThKg8&feature=youtu.be&ab_channel=%D0%A2%D0%B8%D0%BD%D1%8C%D0%BA%D0%BE%D1%84%D1%84%D0%9E%D0%B1%D1%80%D0%B0%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5");

        // WHEN
        ParserAnswer actual = allLinkParser.getLinkParser().parseLink(link);

        // THEN
        assertNull(actual);
    }
}
