package service;

import edu.java.bot.exception.service.ScrapperException;
import java.net.URI;
import java.util.List;

public interface ScrapperService {
    List<String> getUserLinks(long chatId) throws ScrapperException;

    void registerChat(long chatId);

    void addLink(long chatId, URI link) throws ScrapperException;

    void removeLink(long chatId, URI link) throws ScrapperException;
}
