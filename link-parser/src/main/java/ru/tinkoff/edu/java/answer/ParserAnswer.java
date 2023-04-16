package ru.tinkoff.edu.java.answer;

public sealed abstract class ParserAnswer permits GitHubAnswer, StackOverflowAnswer {

    public abstract String toString();
}
