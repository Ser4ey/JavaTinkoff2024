package edu.java.scrapper.model.entity;

import edu.java.scrapper.model.Chat;
import edu.java.scrapper.model.Link;
import java.net.URI;

public class EntityMapper {
    private EntityMapper() {}

    public static Chat toChat(ChatEntity chatEntity) {
        return new Chat(chatEntity.getChatId());
    }

    public static Link toLink(LinkEntity linkEntity) {
        return new Link(
            linkEntity.getId(),
            URI.create(linkEntity.getUrl()),
            linkEntity.getLastUpdate(),
            linkEntity.getLastCheck()
        );
    }
}
