package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.entity.LinkEntity;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JpaLinkRepository extends JpaRepository<LinkEntity, Integer> {
    @NotNull
    Optional<LinkEntity> findByUrl(@NotNull String url);

    @NotNull
    Optional<LinkEntity> findById(@NotNull Integer id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM link WHERE id = ?", nativeQuery = true)
    void deleteById(@NotNull Integer id);

    @NotNull
    @Query(value = "SELECT * FROM link ORDER BY last_check LIMIT :numberOfLink", nativeQuery = true)
    List<LinkEntity> findNotCheckedForLongTime(@Param("numberOfLink") int numberOfLink);

    @Query(value = "SELECT COUNT(*) FROM chat_link WHERE chat_link.chat_id = :chatId AND chat_link.link_id = :linkId",
           nativeQuery = true)
    int countAllByChatIdAndLinkId(long chatId, int linkId);

    @Modifying
    @Transactional
    @Query(value = """
                DELETE FROM link WHERE id IN (
                SELECT link.id FROM link LEFT JOIN chat_link ON link.id = chat_link.link_id
                WHERE chat_link.link_id IS NULL
            )""",
           nativeQuery = true)
    void deleteLinksWithNoRelations();

}
