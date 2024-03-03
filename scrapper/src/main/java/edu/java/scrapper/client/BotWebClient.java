package edu.java.scrapper.client;

import edu.java.scrapper.model.dto.LinkUpdateRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
public class BotWebClient implements BotClient {
    private static final String DEFAULT_URL = "http://localhost:8090";
    private final WebClient webClient;

    public BotWebClient(String baseUrl) {
        String finalUrl = (baseUrl == null || baseUrl.isBlank()) ? DEFAULT_URL : baseUrl;

        this.webClient = WebClient.builder().baseUrl(finalUrl).build();
    }

    @Override
    public void sendUpdates(LinkUpdateRequest linkUpdateRequest) {
        Mono<ClientResponse> responseMono = webClient.post()
            .uri("/updates")
            .bodyValue(linkUpdateRequest)
            .exchange();

        ClientResponse response = responseMono.block();

        if (response == null) {
            log.error("Response is null!");
            return;
        }

        int statusCode = response.statusCode().value();
        String responseBody = response.bodyToMono(String.class).block();

        if (statusCode != HttpStatus.OK.value()) {
            log.error(
                "Error when updates | Status code: {} | Response: {} ",
                statusCode,
                responseBody
            );
        }

    }
}
