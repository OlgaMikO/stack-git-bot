package ru.tinkoff.edu.java.parser;

final class GitHubAnswer extends ParserAnswer {

    private String user;
    private String repository;

    public GitHubAnswer(String user, String repository) {
        super();
        this.user = user;
        this.repository = repository;

    }

    public String toString(){
        return String.format("user: %s\n", user) +
                String.format("repository: %s\n", repository);
    }

}
