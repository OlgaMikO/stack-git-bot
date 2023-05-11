package ru.tinkoff.edu.java.answer;

public sealed abstract class ParserAnswer permits GitHubAnswer, StackOverflowAnswer {

    private final String className;

    public ParserAnswer(String className) {
        this.className = className;
    }

    public abstract String toString();

    public String getClassName() {
        return className;
    }
}
