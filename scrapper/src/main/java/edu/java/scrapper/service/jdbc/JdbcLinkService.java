package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Log4j2
@Service
public class JdbcLinkService implements LinkService {
    private final ChatRepository chatRepository;
    private final LinkRepository linkRepository;

    private static final String NO_CHAT_LOG = "Чата с {} не существует";
    private static final String NO_LINK_LOG = "Ссылки {} не существует";

    @Override
    public Optional<Link> add(long chatId, URI url) {
        if (!chatRepository.isChatExist(chatId)) {
            log.info(NO_CHAT_LOG, chatId);
            return Optional.empty();
        }

        linkRepository.add(chatId, url);
        return linkRepository.findByUrl(url);
    }

    @Override
    @SuppressWarnings("ReturnCount")
    public void remove(long chatId, URI url) {
        if (!chatRepository.isChatExist(chatId)) {
            log.info(NO_CHAT_LOG, chatId);
            return;
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
            log.info(NO_CHAT_LOG, chatId);
            return Collections.emptyList();
        }

        return linkRepository.findAll(chatId);
    }
}
