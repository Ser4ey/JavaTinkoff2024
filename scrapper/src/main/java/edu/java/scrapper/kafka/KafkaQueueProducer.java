package edu.java.scrapper.kafka;

import edu.java.scrapper.model.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaQueueProducer {

    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public void send(LinkUpdateRequest update) {
        kafkaTemplate.send(topic, update);
    }
}
