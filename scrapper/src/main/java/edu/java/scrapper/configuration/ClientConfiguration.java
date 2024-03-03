package edu.java.scrapper.configuration;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.client.BotWebClient;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.GitHubWebClient;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.client.StackOverflowWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Value("${web-clients.github.baseurl:#{null}}")
    private String gitHubBaseUrl;

    @Value("${web-clients.stackoverflow.baseurl:#{null}}")
    private String stackOverflowBaseUrl;

    @Value("${web-clients.bot.baseurl:#{null}}")
    private String botBaseUrl;

    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubWebClient(gitHubBaseUrl);
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowWebClient(stackOverflowBaseUrl);
    }

    @Bean
    public BotClient botClient() {
        return new BotWebClient(botBaseUrl);
    }
}
