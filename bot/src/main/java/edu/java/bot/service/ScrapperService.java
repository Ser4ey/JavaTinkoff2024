package edu.java.bot.service;

import java.net.URI;
import java.util.List;

public interface ScrapperService {
    List<String> getUserLinks(long chatId);

    void registerChat(long chatId);

    void addLink(long chatId, URI link);

    void removeLink(long chatId, URI link);
}
