package edu.java.scrapper.service.impl;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.model.Chat;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.dto.LinkUpdateRequest;
import edu.java.scrapper.service.ChatService;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.LinkUpdater;
import edu.java.scrapper.urls.UrlsApi;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ImplLinkUpdater implements LinkUpdater {

    private final ChatService chatService;

    private final LinkService linkService;

    private final BotClient botClient;

    private final UrlsApi urlsApi;

    @Override
    public int update() {
        int updatedLinkCounter = 0;
        var allLinks = linkService.listAll();

        for (Link link : allLinks) {
            if (updateLink(link)) {
                updatedLinkCounter += 1;
            }
        }

        return updatedLinkCounter;
    }

    private boolean updateLink(Link link) {
        Optional<OffsetDateTime> newTime = urlsApi.getLastActivity(link.url());
        if (newTime.isEmpty()) {
            log.warn("Не удалось получить обновление для ссылки: {}", link.url().toString());
            return false;
        }

        log.debug("Старое время: {} Новок время: {}", link.lastCheckTime(), newTime.get());

        if (link.lastCheckTime().isEqual(newTime.get()) || link.lastCheckTime().isAfter(newTime.get())) {
            log.info("Нет новых обновлений для ссылки: {}", link.url().toString());
            return false;
        }


        log.info("Новое обновление для ссылки: {}", link.url().toString());
        linkService.update(link.id(), newTime.get());

        var chats = chatService.findAll(link.id());
        List<Long> chatIds = new ArrayList<>();
        for (Chat chat : chats) {
            chatIds.add(chat.chatId());
        }


        log.info("Отправляем обновление для: {}", chats);
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
            System.currentTimeMillis(),
           link.url(),
           "Новое обновление для: " + link.url().toString(),
            chatIds
        );
        botClient.sendUpdates(linkUpdateRequest);

        return true;
    }


}
