package edu.java.bot.configuration;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.client.ScrapperRetryWebClient;
import edu.java.bot.client.ScrapperWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class ClientConfiguration {
    @Value("${web-clients.scrapper.baseurl:#{null}}")
    private String scrapperBaseUrl;

    @Autowired
    RetryTemplate retryTemplate;

    @Bean
    public ScrapperClient scrapperClient() {
        var scrapperWebClient = new ScrapperWebClient(scrapperBaseUrl);

        return new ScrapperRetryWebClient(scrapperWebClient, retryTemplate);
    }
}
