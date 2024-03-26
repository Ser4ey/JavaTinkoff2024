package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.entity.Link;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLinkRepository extends JpaRepository<Link, Integer> {
    @NotNull
    Optional<Link> findByUrl(String url);
}
