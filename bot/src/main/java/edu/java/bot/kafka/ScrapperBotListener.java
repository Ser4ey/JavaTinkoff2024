package edu.java.bot.kafka;

import edu.java.bot.model.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScrapperBotListener {

    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

//    @Value("${kafka.dlq-topic.name}")
//    private String dlqTopic;



    @Value("${kafka.topic}")
    private String topic;

//    @KafkaListener
//    (topics = "${kafka.topic}", containerFactory = "kafkaListenerContainerFactory", errorHandler = "errorHandler")
    @KafkaListener(topics = "${kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(LinkUpdateRequest update) {
        log.info("GOOOOOOOOOD");
        log.info(update);
//        log.info("GOOOOOOOOOD");
        // Обработка сообщения
        // Если обработка завершилась неудачей, бросайте исключение
    }

//    @Bean
//    public SeekToCurrentErrorHandler errorHandler() {
//        return new SeekToCurrentErrorHandler((record, ex) -> {
//            // Если обработка завершилась неудачей, отправьте сообщение в DLQ
//            kafkaTemplate.send(dlqTopic, record.value());
//        });
//    }
}
