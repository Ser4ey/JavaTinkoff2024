package edu.java.bot.configuration;

import edu.java.bot.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Value("${web-clients.scrapper.baseurl:#{null}}")
    private String scrapperBaseUrl;

    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperWebClient(scrapperBaseUrl);
    }
}
