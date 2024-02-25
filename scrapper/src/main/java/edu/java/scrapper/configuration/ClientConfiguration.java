package edu.java.scrapper.configuration;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.GitHubWebClient;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.client.StackOverflowWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    private static final String GITHUB_DEFAULT_URL = "https://api.github.com";
    private static final String STACKOVERFLOW_DEFAULT_URL = "https://api.stackexchange.com/2.3/";

    @Value("${web-clients.github.baseurl:#{null}}")
    private String gitHubBaseUrl;

    @Value("${web-clients.stackoverflow.baseurl:#{null}}")
    private String stackOverflowBaseUrl;

    @Bean
    public GitHubClient gitHubClient() {
        String baseUrl = (gitHubBaseUrl == null || gitHubBaseUrl.isBlank()) ? GITHUB_DEFAULT_URL : gitHubBaseUrl;
        return new GitHubWebClient(baseUrl);
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        String baseUrl = (stackOverflowBaseUrl == null || stackOverflowBaseUrl.isBlank()) ? STACKOVERFLOW_DEFAULT_URL
            : stackOverflowBaseUrl;
        return new StackOverflowWebClient(baseUrl);
    }
}
