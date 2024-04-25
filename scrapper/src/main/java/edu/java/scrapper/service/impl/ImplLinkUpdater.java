package edu.java.scrapper.service.impl;

import edu.java.scrapper.model.Chat;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.dto.request.LinkUpdateRequest;
import edu.java.scrapper.service.ChatService;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.LinkUpdater;
import edu.java.scrapper.service.NotificationService;
import edu.java.scrapper.urls.UrlUpdateDto;
import edu.java.scrapper.urls.UrlsApi;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Log4j2
@Service
@RequiredArgsConstructor
public class ImplLinkUpdater implements LinkUpdater {

    private final ChatService chatService;

    private final LinkService linkService;

    private final UrlsApi urlsApi;

    private final NotificationService notificationService;

    @Override
    public int update(int checkedLinksBatchSize) {
        int updatedLinkCounter = 0;
        var allLinks = linkService.listNotCheckedForLongTime(checkedLinksBatchSize);

        for (Link link : allLinks) {
            if (updateLink(link)) {
                updatedLinkCounter += 1;
            }
        }

        return updatedLinkCounter;
    }

    private boolean updateLink(Link link) {
        linkService.updateLastCheckTime(link.id(), OffsetDateTime.now());
        Optional<UrlUpdateDto> urlUpdateDtoOptional = urlsApi.getUrlUpdate(link);
        if (urlUpdateDtoOptional.isEmpty()) {
            log.info("Нет новых обновлений для ссылки: {}", link.url().toString());
            return false;
        }

        UrlUpdateDto urlUpdateDto = urlUpdateDtoOptional.get();
        log.info("Новое обновление для ссылки {}: {}", link.url().toString(), urlUpdateDto);

        var chats = chatService.findAllByLinkId(link.id());
        List<Long> chatIds = new ArrayList<>();
        for (Chat chat : chats) {
            chatIds.add(chat.chatId());
        }
        log.info("Отправляем обновление для: {}", chats);
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
            System.currentTimeMillis(),
            link.url(),
            urlUpdateDto.updateText(),
            chatIds
        );


        try {
            notificationService.sendNotification(linkUpdateRequest);

            linkService.updateLastUpdateTime(link.id(), urlUpdateDto.newLastActivity());
            linkService.updateCount(link.id(), urlUpdateDto.newCount());
        } catch (WebClientRequestException ex) {
            log.error("Не удалось отправить обновление боту: {}", ex.getMessage());
            return false;
        }

        return true;
    }


}
