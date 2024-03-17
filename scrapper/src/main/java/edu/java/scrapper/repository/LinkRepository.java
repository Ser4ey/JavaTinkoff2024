package edu.java.scrapper.repository;

import edu.java.scrapper.model.Link;
import java.net.URI;
import java.util.List;
import java.util.Optional;

public interface LinkRepository {
    List<Link> findAll();

    List<Link> findAll(Integer chatId);

    Optional<Link> findByUrl(URI url);

    void add(Integer chatId, URI url);

    void remove(Integer id);

    void remove(Integer chatId, Integer linkId);
}
