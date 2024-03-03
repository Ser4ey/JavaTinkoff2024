package edu.java.bot.client;

import edu.java.bot.model.dto.AddLinkRequest;
import edu.java.bot.model.dto.LinkResponse;
import edu.java.bot.model.dto.ListLinksResponse;
import edu.java.bot.model.dto.RemoveLinkRequest;
import java.util.Optional;

public interface ScrapperClient {

    void registerChat(Integer id);

    void deleteChat(Integer id);

    Optional<ListLinksResponse> getLinks(Integer id);

    Optional<LinkResponse> addLink(Integer id, AddLinkRequest addLinkRequest);

    Optional<LinkResponse> removeLink(Integer id, RemoveLinkRequest removeLinkRequest);
}
