package edu.java.scrapper.model.entity;

import edu.java.scrapper.model.Chat;
import edu.java.scrapper.model.Link;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class EntityMapper {
    private EntityMapper() {}

    public static Chat toChat(ChatEntity chatEntity) {
        return new Chat(chatEntity.getChatId());
    }

    public static List<Chat> convertListOfChatEntityToListOfChat(List<ChatEntity> listOfChatEntity) {
        var listOfChats = new ArrayList<Chat>();
        for (ChatEntity chatEntity : listOfChatEntity) {
            listOfChats.add(EntityMapper.toChat(chatEntity));
        }
        return listOfChats;
    }


    public static Link toLink(LinkEntity linkEntity) {
        return new Link(
            linkEntity.getId(),
            URI.create(linkEntity.getUrl()),
            linkEntity.getLastUpdate(),
            linkEntity.getLastCheck(),
            linkEntity.getCount()
        );
    }

    public static List<Link> convertListOfLinkEntityToListOfLink(List<LinkEntity> listOfLinkEntity) {
        var listOfLinks = new ArrayList<Link>();
        for (LinkEntity linkEntity : listOfLinkEntity) {
            listOfLinks.add(EntityMapper.toLink(linkEntity));
        }
        return listOfLinks;
    }

}
