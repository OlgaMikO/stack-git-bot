package ru.tinkoff.edu.java.answer;

public final class GitHubAnswer extends ParserAnswer {

    private String user;
    private String repository;

    public GitHubAnswer(String user, String repository) {
        super();
        this.user = user;
        this.repository = repository;

    }

    @Override
    public String toString() {
        return "GitHubAnswer{" +
                "user='" + user + '\'' +
                ", repository='" + repository + '\'' +
                '}';
    }

}
