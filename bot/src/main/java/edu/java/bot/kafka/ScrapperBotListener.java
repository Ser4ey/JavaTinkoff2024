package edu.java.bot.kafka;

import edu.java.bot.model.dto.request.LinkUpdateRequest;
import edu.java.bot.service.UpdateUrlsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Log4j2
@Validated
public class ScrapperBotListener {

    private final UpdateUrlsService updateUrlsService;

    @RetryableTopic(attempts = "1", kafkaTemplate = "kafkaTemplate", dltTopicSuffix = "_dlq")
    @KafkaListener(topics = "${app.kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Valid LinkUpdateRequest update) {
        log.info("Получено обновление по Kafka: {}", update);
        updateUrlsService.update(update);
    }
}
