package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.exception.service_exceptions.LinkAlreadyTracking;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
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

    private static final String NO_LINK_LOG = "Ссылки {} не существует";

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
    @SuppressWarnings("ReturnCount")
    public void remove(long chatId, URI url) {
        if (!chatRepository.isChatExist(chatId)) {
            chatRepository.add(chatId);
        }

        var link = linkRepository.findByUrl(url);
        if (link.isEmpty()) {
            log.info(NO_LINK_LOG, url);
            return;
        }

        linkRepository.removeLinkRelation(chatId, link.get().id());
    }

    @Override
    public List<Link> listAll(long chatId) {
        if (!chatRepository.isChatExist(chatId)) {
            chatRepository.add(chatId);
        }

        return linkRepository.findAll(chatId);
    }
}
