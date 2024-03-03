package edu.java.bot.client;

import edu.java.bot.exception.CustomRequestException;
import edu.java.bot.model.dto.AddLinkRequest;
import edu.java.bot.model.dto.ApiErrorResponse;
import edu.java.bot.model.dto.LinkResponse;
import edu.java.bot.model.dto.ListLinksResponse;
import edu.java.bot.model.dto.RemoveLinkRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperWebClient implements ScrapperClient {
    private static final String DEFAULT_URL = "http://localhost:8080";
    private final WebClient webClient;

    public ScrapperWebClient(String baseUrl) {
        String finalUrl = (baseUrl == null || baseUrl.isBlank()) ? DEFAULT_URL : baseUrl;

        this.webClient = WebClient.builder().baseUrl(finalUrl).build();
    }

    @Override
    public void registerChat(Integer id) {
        webClient.post()
            .uri("/tg-chat/{id}", id)
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                .flatMap(apiErrorResponse -> Mono.error(new CustomRequestException(apiErrorResponse)))
            )
            .toBodilessEntity()
            .block();
    }

    @Override
    public void deleteChat(Integer id) {
        webClient.delete()
            .uri("/tg-chat/{id}", id)
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                .flatMap(apiErrorResponse -> Mono.error(new CustomRequestException(apiErrorResponse)))
            )
            .toBodilessEntity()
            .block();
    }

    @Override
    public ListLinksResponse getLinks(Integer id) {
        return webClient.get()
            .uri("/links")
            .header("Tg-Chat-Id", id.toString())
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                .flatMap(apiErrorResponse -> Mono.error(new CustomRequestException(apiErrorResponse)))
            )
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    @Override
    public LinkResponse addLink(Integer id, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri("/links")
            .header("Tg-Chat-Id", id.toString())
            .bodyValue(addLinkRequest)
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                .flatMap(apiErrorResponse -> Mono.error(new CustomRequestException(apiErrorResponse)))
            )
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse removeLink(Integer id, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
            .uri("/links")
            .header("Tg-Chat-Id", id.toString())
            .bodyValue(removeLinkRequest)
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                .flatMap(apiErrorResponse -> Mono.error(new CustomRequestException(apiErrorResponse)))
            )
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
