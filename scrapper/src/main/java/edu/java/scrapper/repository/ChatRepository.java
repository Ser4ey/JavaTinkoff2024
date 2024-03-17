package edu.java.scrapper.repository;

import edu.java.scrapper.model.Chat;
import java.util.List;
import java.util.Optional;

public interface ChatRepository {
    List<Chat> findAll();

    Optional<Chat> findByUniqueChatId(Long uniqueChatId);

    void add(Long uniqueChatId);

    void remove(Integer id);
}
