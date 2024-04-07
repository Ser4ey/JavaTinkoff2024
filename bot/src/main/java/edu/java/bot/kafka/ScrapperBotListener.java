package edu.java.bot.kafka;

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

//    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

//    @Value("${kafka.topic}")
//    private String topic;

    @RetryableTopic(attempts = "1", kafkaTemplate = "kafkaTemplate", dltTopicSuffix = "_dlq")
    @KafkaListener(topics = "${kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(LinkUpdateRequest update) throws Exception {
//        if (update.id() == 1712451729582L) {
//            throw new Exception();
//        }
        log.info("GOOOOOOOOOD");
        log.info(update);
    }
}
