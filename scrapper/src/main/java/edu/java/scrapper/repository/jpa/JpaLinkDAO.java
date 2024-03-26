package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkDAO implements LinkRepository {
    private final JpaLinkRepository jpaLinkRepository;

    @Override
    public List<Link> findAll() {
        return null;
    }

    @Override
    public List<Link> findNotCheckedForLongTime(Integer numberOfLink) {
        return null;
    }

    @Override
    public List<Link> findAllByChatId(Long chatId) {
        return null;
    }

    @Override
    public Optional<Link> findById(Integer linkId) {
        return Optional.empty();
    }

    @Override
    public Optional<Link> findByUrl(URI url) {
        return Optional.empty();
    }

    @Override
    public Optional<Link> findByChatIdAndLinkId(Long chatId, Integer linkId) {
        return Optional.empty();
    }

    @Override
    public Link addLink(URI url) {
        return null;
    }

    @Override
    public void addLinkRelation(Long chatId, Integer linkId) {

    }

    @Override
    public void updateLastUpdateTime(Integer id, OffsetDateTime lastUpdateTime) {

    }

    @Override
    public void updateLastCheckTime(Integer id, OffsetDateTime lastCheckTime) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void removeLinkRelation(Long chatId, Integer linkId) {

    }

    @Override
    public void removeLinksWithNoRelations() {

    }
}
