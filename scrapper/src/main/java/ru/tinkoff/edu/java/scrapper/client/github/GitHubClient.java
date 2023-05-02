package ru.tinkoff.edu.java.scrapper.client.github;

import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;

public interface GitHubClient {

    GitHubResponse fetchRepository(String user, String repository);

}
