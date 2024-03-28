package edu.java.scrapper.repository;

import edu.java.scrapper.model.Chat;
import java.util.List;

public interface ChatRepository {
    List<Chat> findAll();

    List<Chat> findAllByLinkId(Integer linkId);

    boolean isChatExist(Long chatId);

    void add(Long chatId);

    void remove(Long chatId);
}
