package edu.java.scrapper.repository;

import edu.java.scrapper.model.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkRepository {
    List<Link> findAll();

    List<Link> findAllByChatId(Long chatId);

    Optional<Link> findById(Integer linkId);

    Optional<Link> findByUrl(URI url);

    Optional<Link> findByChatIdAndLinkId(Long chatId, Integer linkId);

    Link add(Long chatId, URI url);

    void updateLastUpdateTime(Integer id, OffsetDateTime lastUpdateTime);

    void updateLastCheckTime(Integer id, OffsetDateTime lastCheckTime);

    void remove(Integer id);

    void removeLinkRelation(Long chatId, Integer linkId); // удаление из таблицы chat_link
}
