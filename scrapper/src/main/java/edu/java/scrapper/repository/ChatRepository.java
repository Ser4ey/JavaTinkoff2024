package edu.java.scrapper.repository;

import edu.java.scrapper.model.Chat;
import java.util.List;
import java.util.Optional;

public interface ChatRepository {
    List<Chat> findAll();

    boolean isChatExist(Long uniqueChatId);

    void add(Long chatId);

    void remove(Long chatId);
}
