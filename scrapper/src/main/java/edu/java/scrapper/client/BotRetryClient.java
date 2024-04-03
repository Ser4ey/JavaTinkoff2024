package edu.java.scrapper.client;

import edu.java.scrapper.model.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.support.RetryTemplate;

@RequiredArgsConstructor
public class BotRetryClient implements BotClient {

    private final BotClient botClient;

    private final RetryTemplate retryTemplate;

    @Override
    public void sendUpdates(LinkUpdateRequest linkUpdateRequest) {
        retryTemplate.execute(context -> {
            botClient.sendUpdates(linkUpdateRequest);
            return null;
        });
    }
}
