package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.entity.LinkEntity;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JpaLinkDAO implements LinkRepository {
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinkRepository jpaLinkRepository;

    private List<Link> convertListOfLinkEntityToListOfLink(List<LinkEntity> listOfLinkEntity) {
        var listOfLinks = new ArrayList<Link>();
        for (LinkEntity linkEntity : listOfLinkEntity) {
            listOfLinks.add(linkEntity.toLink());
        }
        return listOfLinks;
    }

    @Override
    public List<Link> findAll() {
        return convertListOfLinkEntityToListOfLink(jpaLinkRepository.findAll());
    }

    @Override
    public List<Link> findNotCheckedForLongTime(Integer numberOfLink) {
        return convertListOfLinkEntityToListOfLink(
            jpaLinkRepository.findNotCheckedForLongTime(numberOfLink));
    }

    @Override
    @Transactional
    public List<Link> findAllByChatId(Long chatId) {
        var chatEntity = jpaChatRepository.findByChatId(chatId);
        if (chatEntity.isEmpty()) {
            return new ArrayList<>();
        }

        return convertListOfLinkEntityToListOfLink(chatEntity.get().getLinks());
    }

    @Override
    public Optional<Link> findById(Integer linkId) {
        var linkEntity = jpaLinkRepository.findById(linkId);
        return linkEntity.map(LinkEntity::toLink);
    }

    @Override
    public Optional<Link> findByUrl(URI url) {
        var linkEntity = jpaLinkRepository.findByUrl(url.toString());
        return linkEntity.map(LinkEntity::toLink);
    }

    @Override
    @Transactional
    public Optional<Link> findByChatIdAndLinkId(Long chatId, Integer linkId) {
        int count = jpaLinkRepository.countAllByChatIdAndLinkId(chatId, linkId);
        if (count == 0) {
            return Optional.empty();
        }

        return findById(linkId);
    }

    @Override
    public Link addLink(URI url) {
        jpaLinkRepository.save(
            new LinkEntity(url.toString())
        );
        return findByUrl(url).get();
    }

    @Override
    @Transactional
    public void addLinkRelation(Long chatId, Integer linkId) {
        var chatEntity = jpaChatRepository.findById(chatId);
        var linkEntity = jpaLinkRepository.findById(linkId);

        chatEntity.get().getLinks().add(linkEntity.get());
        linkEntity.get().getChats().add(chatEntity.get());

        jpaChatRepository.save(chatEntity.get());
        jpaLinkRepository.save(linkEntity.get());
    }

    @Override
    @Transactional
    public void updateLastUpdateTime(Integer id, OffsetDateTime lastUpdateTime) {
        var linkEntity = jpaLinkRepository.findById(id);
        if (linkEntity.isEmpty()) {
            return;
        }
        linkEntity.get().setLastUpdate(lastUpdateTime);
        jpaLinkRepository.save(linkEntity.get());
    }

    @Override
    @Transactional
    public void updateLastCheckTime(Integer id, OffsetDateTime lastCheckTime) {
        var linkEntity = jpaLinkRepository.findById(id);
        if (linkEntity.isEmpty()) {
            return;
        }
        linkEntity.get().setLastCheck(lastCheckTime);
        jpaLinkRepository.save(linkEntity.get());
    }

    @Override
    @Transactional
    public void remove(Integer id) {
        jpaLinkRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void removeLinkRelation(Long chatId, Integer linkId) {
        var chatEntity = jpaChatRepository.findByChatId(chatId);
        var linkEntity = jpaLinkRepository.findById(linkId);
        if (chatEntity.isEmpty() || linkEntity.isEmpty()) {
            return;
        }

        chatEntity.get().getLinks().remove(linkEntity.get());
        linkEntity.get().getChats().remove(chatEntity.get());

        jpaChatRepository.save(chatEntity.get());
        jpaLinkRepository.save(linkEntity.get());
    }

    @Override
    @Transactional
    public void removeLinksWithNoRelations() {
        jpaLinkRepository.deleteLinksWithNoRelations();
    }
}
