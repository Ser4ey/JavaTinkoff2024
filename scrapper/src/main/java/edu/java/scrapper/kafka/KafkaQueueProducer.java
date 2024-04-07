package edu.java.scrapper.kafka;

import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.model.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaQueueProducer {

    private final ApplicationConfig applicationConfig;
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    public void send(LinkUpdateRequest update) {
        kafkaTemplate.send(applicationConfig.kafka().topic(), update);
    }
}
