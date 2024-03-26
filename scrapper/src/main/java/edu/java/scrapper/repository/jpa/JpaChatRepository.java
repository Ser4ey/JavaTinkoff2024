package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.entity.ChatEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRepository extends JpaRepository<ChatEntity, Long> {
    Optional<ChatEntity> findChatByChatId(long chatId);

    boolean existsChatByChatId(long chatId);

}
