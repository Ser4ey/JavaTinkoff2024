package edu.java.bot.client;

import edu.java.bot.model.dto.response.LinkResponse;
import edu.java.bot.model.dto.response.ListLinksResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.support.RetryTemplate;

@RequiredArgsConstructor
public class ScrapperRetryClient implements ScrapperClient {
    private final ScrapperClient scrapperClient;

    private final RetryTemplate retryTemplate;

    @Override
    public void registerChat(Long id) {
        retryTemplate.execute(context -> {
            scrapperClient.registerChat(id);
            return null;
        });
    }

    @Override
    public void deleteChat(Long id) {
        retryTemplate.execute(context -> {
            scrapperClient.deleteChat(id);
            return null;
        });
    }

    @Override
    public ListLinksResponse getLinks(Long id) {
        return retryTemplate.execute(context -> scrapperClient.getLinks(id));
    }

    @Override
    public LinkResponse addLink(Long id, URI link) {
        return retryTemplate.execute(context -> scrapperClient.addLink(id, link));
    }

    @Override
    public LinkResponse removeLink(Long id, URI link) {
        return retryTemplate.execute(context -> scrapperClient.removeLink(id, link));
    }
}
