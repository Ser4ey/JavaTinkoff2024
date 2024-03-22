package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.exception.service_exceptions.LinkAlreadyTracking;
import edu.java.scrapper.exception.service_exceptions.LinkNotFound;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Log4j2
@Service
public class JdbcLinkService implements LinkService {
    private final ChatRepository chatRepository;
    private final LinkRepository linkRepository;

    @Override
    @Transactional
    public Link add(long chatId, URI url) throws LinkAlreadyTracking {
        if (!chatRepository.isChatExist(chatId)) {
            chatRepository.add(chatId);
        }

        var link = linkRepository.findByUrl(url);
        if (link.isPresent()) {
            if (linkRepository.findByChatIdAndLinkId(chatId, link.get().id()).isPresent()) {
                throw new LinkAlreadyTracking();
            }
        }

        return linkRepository.add(chatId, url);
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
    @Transactional
    @SuppressWarnings("ReturnCount")
    public void remove(long chatId, URI url) throws LinkNotFound {
        if (!chatRepository.isChatExist(chatId)) {
            chatRepository.add(chatId);
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
    public List<Link> listAllByChatId(long chatId) {
        if (!chatRepository.isChatExist(chatId)) {
            chatRepository.add(chatId);
        }

        return linkRepository.findAllByChatId(chatId);
    }

    @Override
    public List<Link> listAll() {
        return linkRepository.findAll();
    }
}

