package edu.java.scrapper.configuration;

import edu.java.scrapper.kafka.KafkaQueueProducer;
import edu.java.scrapper.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Log4j2
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class KafkaNotificationServiceAccessConfiguration {

    private final KafkaQueueProducer kafkaQueueProducer;

    @Bean
    public NotificationService notificationService() {
        return update -> {
            log.debug("Используем Kafaka для отправки LinkUpdateRequest");
            kafkaQueueProducer.send(update);
        };
    }
}

