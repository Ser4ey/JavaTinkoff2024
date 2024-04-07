package edu.java.bot.kafka;

import edu.java.bot.exception.kafka.MessageValueValidationException;
import edu.java.bot.kafka.validator.LinkUpdateRequestValidator;
import edu.java.bot.model.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScrapperBotListener {

    private final LinkUpdateRequestValidator linkUpdateRequestValidator;

    @RetryableTopic(attempts = "1", kafkaTemplate = "kafkaTemplate", dltTopicSuffix = "_dlq")
    @KafkaListener(topics = "${kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(LinkUpdateRequest update) {

        try {
            linkUpdateRequestValidator.validate(update);
        } catch (MessageValueValidationException e) {
            log.warn("Ошибка валидация: {}", e.getValidationProblem());
            throw e;
        }

        log.info("GOOOOOOOOOD");
        log.info(update);
    }
}
