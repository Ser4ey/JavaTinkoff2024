package edu.java.scrapper.client;

import edu.java.scrapper.model.dto.response.GitHubOwnerRepoResponse;


public interface GitHubClient {
    GitHubOwnerRepoResponse getRepository(String owner, String repo);
}
