package edu.java.bot.model.dto;

import edu.java.scrapper.model.dto.StackOverflowQuestionsResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Log4j2
public class StackOverflowWebClient implements StackOverflowClient {

    private static final String DEFAULT_URL = "https://api.stackexchange.com/2.3/";
    private final WebClient webClient;

    public StackOverflowWebClient(String baseUrl) {
        String finalUrl = (baseUrl == null || baseUrl.isBlank()) ? DEFAULT_URL : baseUrl;

        this.webClient = WebClient.builder().baseUrl(finalUrl).build();
    }

    @Override
    public StackOverflowQuestionsResponse.StackOverflowQuestion getQuestion(Long id) {
        try {
            StackOverflowQuestionsResponse stackOverflowQuestionsResponse = webClient.get()
                .uri("/questions/{id}?order=desc&sort=activity&site=stackoverflow", id)
                .retrieve()
                .bodyToMono(StackOverflowQuestionsResponse.class)
                .block();

            if (stackOverflowQuestionsResponse == null) {
                log.error("StackOverflowQuestionsResponse is null");
                return null;
            }

            if (stackOverflowQuestionsResponse.questions().isEmpty()) {
                log.info("The question does not exist");
                return null;
            }

            return stackOverflowQuestionsResponse.questions().get(0);

        } catch (WebClientResponseException ex) {
            log.error(
                "Error fetching question | Status code: {} | Response: {} ",
                ex.getStatusCode(),
                ex.getResponseBodyAsString()
            );
            return null;
        }
    }
}
