package ru.tinkoff.edu.java.answer;

public final class GitHubAnswer extends ParserAnswer {

    private final String user;
    private final String repository;

    public String getUser() {
        return user;
    }

    public String getRepository() {
        return repository;
    }

    public GitHubAnswer(String user, String repository) {
        super("GitHubAnswer");
        this.user = user;
        this.repository = repository;

    }

    @Override
    public String toString() {
        return "GitHubAnswer{"
            + "user='" + user + '\''
            + ", repository='" + repository + '\''
            + '}';
    }

}
