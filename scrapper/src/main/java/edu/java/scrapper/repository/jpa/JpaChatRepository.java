package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.entity.ChatEntity;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaChatRepository extends JpaRepository<ChatEntity, Long> {
    Optional<ChatEntity> findByChatId(long chatId);

    boolean existsChatByChatId(long chatId);

    @Modifying
    @Query(value = "DELETE FROM chat WHERE chat_id = ?", nativeQuery = true)
    void deleteById(@NotNull Long chatId);

}
