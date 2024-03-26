package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.entity.Chat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findChatByChatId(long chatId);

    boolean existsChatByChatId(long chatId);

}
