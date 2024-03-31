package edu.java.bot.client;

import edu.java.bot.exception.request.CustomRequestException;
import edu.java.bot.model.dto.request.AddLinkRequest;
import edu.java.bot.model.dto.request.RemoveLinkRequest;
import edu.java.bot.model.dto.response.ApiErrorResponse;
import edu.java.bot.model.dto.response.LinkResponse;
import edu.java.bot.model.dto.response.ListLinksResponse;
import java.net.URI;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperWebClient implements ScrapperClient {
    private static final String DEFAULT_URL = "http://localhost:8080";
    private static final String TG_CHAT_URL = "/tg-chat/{id}";
    private static final String LINKS_URL = "/links";
    private static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";

    private final WebClient webClient;
    private final RetryTemplate retryTemplate;

    public ScrapperWebClient(String baseUrl, RetryTemplate retryTemplate) {
        String finalUrl = (baseUrl == null || baseUrl.isBlank()) ? DEFAULT_URL : baseUrl;

        this.webClient = WebClient.builder().baseUrl(finalUrl).build();

        this.retryTemplate = retryTemplate;
    }

    @Override
    public void registerChat(Long id) {
        retryTemplate.execute(context -> {
            registerChat2(id);
            return null;
        });
    }

    public void registerChat2(Long id) {
        webClient.post()
            .uri(TG_CHAT_URL, id)
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                .flatMap(apiErrorResponse -> Mono.error(new CustomRequestException(apiErrorResponse)))
            )
            .toBodilessEntity()
            .block();
    }

    @Override
    public void deleteChat(Long id) {
        webClient.delete()
            .uri("TG_CHAT_URL", id)
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                .flatMap(apiErrorResponse -> Mono.error(new CustomRequestException(apiErrorResponse)))
            )
            .toBodilessEntity()
            .block();
    }

    @Override
    public ListLinksResponse getLinks(Long id) {
        return webClient.get()
            .uri(LINKS_URL)
            .header(TG_CHAT_ID_HEADER, id.toString())
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                .flatMap(apiErrorResponse -> Mono.error(new CustomRequestException(apiErrorResponse)))
            )
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    @Override
    public LinkResponse addLink(Long id, URI link) {
        AddLinkRequest addLinkRequest = new AddLinkRequest(link);

        return webClient.post()
            .uri(LINKS_URL)
            .header(TG_CHAT_ID_HEADER, id.toString())
            .bodyValue(addLinkRequest)
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                .flatMap(apiErrorResponse -> Mono.error(new CustomRequestException(apiErrorResponse)))
            )
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse removeLink(Long id, URI link) {
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(link);

        return webClient.method(HttpMethod.DELETE)
            .uri(LINKS_URL)
            .header(TG_CHAT_ID_HEADER, id.toString())
            .bodyValue(removeLinkRequest)
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                .flatMap(apiErrorResponse -> Mono.error(new CustomRequestException(apiErrorResponse)))
            )
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
