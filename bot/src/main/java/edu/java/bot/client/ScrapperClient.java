package edu.java.bot.client;

import edu.java.bot.model.dto.AddLinkRequest;
import edu.java.bot.model.dto.LinkResponse;
import edu.java.bot.model.dto.ListLinksResponse;
import edu.java.bot.model.dto.RemoveLinkRequest;

public interface ScrapperClient {

    void registerChat(Long id);

    void deleteChat(Long id);

    ListLinksResponse getLinks(Long id);

    LinkResponse addLink(Long id, AddLinkRequest addLinkRequest);

    LinkResponse removeLink(Long id, RemoveLinkRequest removeLinkRequest);
}
