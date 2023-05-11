package ru.tinkoff.edu.java;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.tinkoff.edu.java.parser.GitHubParser;
import ru.tinkoff.edu.java.parser.LinkParser;
import ru.tinkoff.edu.java.parser.NullParser;
import ru.tinkoff.edu.java.parser.StackOverflowParser;

public final class ParserApplication {

    static Logger logger;

    private ParserApplication() {
    }

    public static void main(String[] args) {
        LinkParser nullParser = new NullParser(null);
        LinkParser githubParser = new GitHubParser(nullParser);
        LinkParser stackoverflowParser = new StackOverflowParser(githubParser);
        String githubLink = "https://github.com/OlgaMikO/stack-git-bot/tree/hw1";
        String stackoverflowLink =
            "https://stackoverflow.com/questions/62147477/java-records-stackoverflow-runtimeexception";
        String otherLink =
            "https://www.youtube.com/watch?v=URQOHdThKg8&feature=youtu.be&ab_channel=%D0%A2%D0%B8%D0%BD%D1%8C%D0%BA%D0%BE%D1%84%D1%84%D0%9E%D0%B1%D1%80%D0%B0%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5";
        logger.log(Level.INFO, stackoverflowParser.parseLink(URI.create(githubLink)).toString());
        logger.log(Level.INFO, stackoverflowParser.parseLink(URI.create(stackoverflowLink)).toString());
        logger.log(Level.INFO, stackoverflowParser.parseLink(URI.create(otherLink)).toString());
    }
}
