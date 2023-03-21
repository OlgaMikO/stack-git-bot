package ru.tinkoff.edu.java.parser;

public sealed abstract class ParserAnswer permits GitHubAnswer, StackOverflowAnswer {

    public abstract String toString();
}
