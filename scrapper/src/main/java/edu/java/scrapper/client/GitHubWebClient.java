package edu.java.scrapper.client;

import edu.java.scrapper.response_dto.GitHubOwnerRepoResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Log4j2
public class GitHubWebClient implements GitHubClient {

    private static final String DEFAULT_URL = "https://api.github.com";
    private final WebClient webClient;

    public GitHubWebClient(String baseUrl) {
        String finalUrl = (baseUrl == null || baseUrl.isBlank()) ? DEFAULT_URL : baseUrl;

        this.webClient = WebClient.builder().baseUrl(finalUrl).build();
    }

    @Override
    public GitHubOwnerRepoResponse getRepository(String owner, String repo) {
        try {
            return webClient.get()
                .uri("/repos/{owner}/{repo}", owner, repo)
                .retrieve()
                .bodyToMono(GitHubOwnerRepoResponse.class)
                .block();
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info(
                    "The repository does not exist | Status code: {} | Response: {} ",
                    ex.getStatusCode(),
                    ex.getResponseBodyAsString()
                );
            } else {
                log.error(
                    "Error fetching repository | Status code: {} | Response: {} ",
                    ex.getStatusCode(),
                    ex.getResponseBodyAsString()
                );
            }
            return null;
        }
    }
}
