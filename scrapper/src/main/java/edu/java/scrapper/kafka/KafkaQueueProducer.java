package edu.java.scrapper.kafka;

import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.model.dto.request.LinkUpdateRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaQueueProducer {

    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    private final String topic;

    public KafkaQueueProducer(
        KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate,
        ApplicationConfig applicationConfig
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = applicationConfig.kafka().topic();
    }

    public void send(LinkUpdateRequest update) {
        kafkaTemplate.send(topic, update);
    }
}
