package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.entity.LinkEntity;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLinkRepository extends JpaRepository<LinkEntity, Integer> {
    @NotNull
    Optional<LinkEntity> findByUrl(String url);
}
