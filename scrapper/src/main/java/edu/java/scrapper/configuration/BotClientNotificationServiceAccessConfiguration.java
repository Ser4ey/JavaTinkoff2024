package edu.java.scrapper.configuration;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Log4j2
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class BotClientNotificationServiceAccessConfiguration {

    private final BotClient botClient;

    @Bean
    public NotificationService notificationService() {
        return update -> {
            log.debug("Используем BotClient для отправки LinkUpdateRequest");
            botClient.sendUpdates(update);
        };
    }
}
