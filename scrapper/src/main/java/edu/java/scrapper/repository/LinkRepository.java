package edu.java.scrapper.repository;

import edu.java.scrapper.model.Link;
import java.net.URI;
import java.util.List;

public interface LinkRepository {
    List<Link> findAll();

    List<Link> findAll(Integer chatId);

    Link add(Integer chatId, URI url);

    void remove(Integer id);
}
