package edu.java.bot.client;

import edu.java.bot.model.dto.LinkResponse;
import edu.java.bot.model.dto.ListLinksResponse;
import java.net.URI;

public interface ScrapperClient {

    void registerChat(Long id);

    void deleteChat(Long id);

    ListLinksResponse getLinks(Long id);

    LinkResponse addLink(Long id, URI link);

    LinkResponse removeLink(Long id, URI link);
}
