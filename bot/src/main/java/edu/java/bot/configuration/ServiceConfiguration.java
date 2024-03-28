package edu.java.bot.configuration;

import edu.java.bot.client.ScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.ScrapperService;
import service.ScrapperServiceImpl;

@Configuration
@RequiredArgsConstructor
public class ServiceConfiguration {
    private final ScrapperClient scrapperClient;

    @Bean
    public ScrapperService scrapperService() {
        return new ScrapperServiceImpl(scrapperClient);
    }
}
