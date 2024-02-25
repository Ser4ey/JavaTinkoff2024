package edu.java.scrapper.configuration;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.GitHubWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ClientConfiguration {
    private static final String GITHUB_DEFAULT_URL = "https://api.github.com";

    @Value("${web-clients.github.baseurl:#{null}}")
    private String gitHubBaseUrl;

    @Bean
    public GitHubClient clientService() {
        String baseUrl = (gitHubBaseUrl == null || gitHubBaseUrl.isBlank()) ? GITHUB_DEFAULT_URL : gitHubBaseUrl;
        return new GitHubWebClient(baseUrl);
    }
}
