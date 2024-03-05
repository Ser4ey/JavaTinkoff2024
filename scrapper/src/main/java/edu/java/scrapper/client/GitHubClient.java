package edu.java.scrapper.client;

import edu.java.scrapper.model.dto.GitHubOwnerRepoResponse;


public interface GitHubClient {
    GitHubOwnerRepoResponse getRepository(String owner, String repo);
}
