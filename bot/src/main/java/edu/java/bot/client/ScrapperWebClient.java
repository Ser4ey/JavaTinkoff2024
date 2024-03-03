package edu.java.bot.client;

import edu.java.bot.model.dto.AddLinkRequest;
import edu.java.bot.model.dto.LinkResponse;
import edu.java.bot.model.dto.ListLinksResponse;
import edu.java.bot.model.dto.RemoveLinkRequest;
import java.util.Optional;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

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
            .bodyToMono(String.class)
            .block();
    }

    @Override
    public void deleteChat(Integer id) {
        webClient.delete()
            .uri("/tg-chat/{id}", id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    @Override
    public Optional<ListLinksResponse> getLinks(Integer id) {
        return webClient.get()
            .uri("/links")
            .header("Tg-Chat-Id", id.toString())
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .blockOptional();
    }

    @Override
    public Optional<LinkResponse> addLink(Integer id, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri("/links")
            .header("Tg-Chat-Id", id.toString())
            .bodyValue(addLinkRequest)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .blockOptional();
    }

    @Override
    public Optional<LinkResponse> removeLink(Integer id, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
            .uri("/links")
            .header("Tg-Chat-Id", id.toString())
            .bodyValue(removeLinkRequest)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .blockOptional();
    }
}
