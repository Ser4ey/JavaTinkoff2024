package edu.java.scrapper.service.impl;

import edu.java.scrapper.exception.service.ChatNotFound;
import edu.java.scrapper.exception.service.LinkAlreadyTracking;
import edu.java.scrapper.exception.service.LinkDoNotWorking;
import edu.java.scrapper.exception.service.LinkNotFound;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.urls.UrlsApi;
import edu.java.scrapper.urls.model.TrackedUrlInfo;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Log4j2
@Service
public class ImplLinkService implements LinkService {
    private final ChatRepository chatRepository;
    private final LinkRepository linkRepository;
    private final UrlsApi urlsApi;

    private final static String CHAT_NOT_FOUND_EXCEPTION_TEXT = "You need to register!";

    @Override
    @Transactional
    public Link add(long chatId, URI url) {
        if (!chatRepository.isChatExist(chatId)) {
            throw new ChatNotFound(CHAT_NOT_FOUND_EXCEPTION_TEXT);
        }

        var link = linkRepository.findByUrl(url);
        if (link.isPresent()) {
            if (linkRepository.findByChatIdAndLinkId(chatId, link.get().id()).isPresent()) {
                throw new LinkAlreadyTracking();
            }

            linkRepository.addLinkRelation(chatId, link.get().id());
            return link.get();
        }

        Optional<TrackedUrlInfo> trackedUrlInfo = urlsApi.getUrlInfo(url);
        if (trackedUrlInfo.isEmpty()) {
            throw new LinkDoNotWorking();
        }

        var newLink = linkRepository.addLink(url, trackedUrlInfo.get().count());
        linkRepository.addLinkRelation(chatId, newLink.id());
        return newLink;
    }

    @Override
    public void updateLastUpdateTime(Integer id, OffsetDateTime lastUpdateTime) {
        linkRepository.updateLastUpdateTime(id, lastUpdateTime);
    }

    @Override
    public void updateLastCheckTime(Integer id, OffsetDateTime lastCheckTime) {
        linkRepository.updateLastCheckTime(id, lastCheckTime);
    }

    @Override
    public void updateCount(Integer id, Integer count) {
        linkRepository.updateCount(id, count);
    }

    @Override
    @Transactional
    public void remove(long chatId, URI url) {
        if (!chatRepository.isChatExist(chatId)) {
            throw new ChatNotFound(CHAT_NOT_FOUND_EXCEPTION_TEXT);
        }

        var link = linkRepository.findByUrl(url);
        if (link.isEmpty()) {
            throw new LinkNotFound();
        }
        if (linkRepository.findByChatIdAndLinkId(chatId, link.get().id()).isEmpty()) {
            throw new LinkNotFound();
        }

        linkRepository.removeLinkRelation(chatId, link.get().id());
    }

    @Override
    public void removeLinksWithNoRelations() {
        linkRepository.removeLinksWithNoRelations();
    }

    @Override
    public List<Link> listAllByChatId(long chatId) {
        if (!chatRepository.isChatExist(chatId)) {
            throw new ChatNotFound(CHAT_NOT_FOUND_EXCEPTION_TEXT);
        }

        return linkRepository.findAllByChatId(chatId);
    }

    @Override
    public List<Link> listAll() {
        return linkRepository.findAll();
    }

    @Override
    public List<Link> listNotCheckedForLongTime(int numberOfLinks) {
        return linkRepository.findNotCheckedForLongTime(numberOfLinks);
    }
}

