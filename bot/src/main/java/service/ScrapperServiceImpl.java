package service;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.exception.request.CustomRequestException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ScrapperServiceImpl implements ScrapperService {

    private final ScrapperClient scrapperClient;

    @Override
    public List<String> getUserLinks(long chatId) {
        var listLinksResponse = scrapperClient.getLinks(chatId);

        List<String> links = new ArrayList<>();

        for (var link : listLinksResponse.links()) {
            links.add(link.url().toString());
        }

        return links;
    }

    @Override
    public void registerChat(long chatId) {
        try {
            scrapperClient.registerChat(chatId);
        } catch (CustomRequestException e) {
            log.info("Не удалось зарегистрировать пользователя", e);
        }
    }

    @Override
    public void addLink(long chatId, URI link) {
        scrapperClient.addLink(chatId, link);
    }

    @Override
    public void removeLink(long chatId, URI link) {
        scrapperClient.removeLink(chatId, link);
    }

}
