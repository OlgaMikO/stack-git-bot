package ru.tinkoff.edu.java.parser;

import java.net.URI;

public class ParserApplication {

    public static void main(String[] args) {
        LinkParser githubParser = new GitHubParser(null);
        LinkParser stackoverflowParser = new StackOverflowParser(githubParser);
        String githubLink = "https://github.com/OlgaMikO/stack-git-bot/tree/hw1";
        String stackoverflowLink = "https://stackoverflow.com/questions/62147477/java-records-stackoverflow-runtimeexception";
        String otherLink = "https://www.youtube.com/watch?v=URQOHdThKg8&feature=youtu.be&ab_channel=%D0%A2%D0%B8%D0%BD%D1%8C%D0%BA%D0%BE%D1%84%D1%84%D0%9E%D0%B1%D1%80%D0%B0%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5";
        System.out.println(stackoverflowParser.parseLink(URI.create(githubLink)));
        System.out.println(stackoverflowParser.parseLink(URI.create(stackoverflowLink)));
        System.out.println(stackoverflowParser.parseLink(URI.create(otherLink)));
    }
}
