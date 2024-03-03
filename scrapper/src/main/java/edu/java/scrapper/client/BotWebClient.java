package edu.java.scrapper.client;

import edu.java.scrapper.exception.CustomRequestException;
import edu.java.scrapper.model.dto.ApiErrorResponse;
import edu.java.scrapper.model.dto.LinkUpdateRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatusCode;

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
        webClient.post()
            .uri("/updates")
            .bodyValue(linkUpdateRequest)
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                .flatMap(apiErrorResponse -> Mono.error(new CustomRequestException(apiErrorResponse)))
            )
            .toBodilessEntity()
            .block();
    }
}
