package edu.java.scrapper.service;

import edu.java.scrapper.exception.service.ChatNotFound;
import edu.java.scrapper.exception.service.LinkAlreadyTracking;
import edu.java.scrapper.exception.service.LinkDoNotWorking;
import edu.java.scrapper.exception.service.LinkNotFound;
import edu.java.scrapper.model.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {
    Link add(long chatId, URI url) throws LinkAlreadyTracking, ChatNotFound, LinkDoNotWorking;

    void updateLastUpdateTime(Integer id, OffsetDateTime lastUpdateTime);

    void updateLastCheckTime(Integer id, OffsetDateTime lastCheckTime);

    void remove(long chatId, URI url) throws LinkNotFound, ChatNotFound;

    void removeLinksWithNoRelations();

    List<Link> listAllByChatId(long chatId);

    List<Link> listAll();

}
