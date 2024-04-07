package edu.java.scrapper.configuration;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.kafka.KafkaQueueProducer;
import edu.java.scrapper.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class NotificationServiceConfiguration {
    @Value("${kafka.use-queue}")
    private boolean useQueue;

    private final KafkaQueueProducer kafkaQueueProducer;
    private final BotClient botClient;

    @Bean
    public NotificationService notificationService() {
        if (useQueue) {
            return update -> {
                // return kafkaQueueProducer::send;
                log.debug("Используем Kafaka для отправки LinkUpdateRequest");
                kafkaQueueProducer.send(update);
            };
        }
        return update -> {
            log.debug("Используем BotClient для отправки LinkUpdateRequest");
            botClient.sendUpdates(update);
        };
    }

}
