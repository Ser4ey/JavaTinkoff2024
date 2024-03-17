package edu.java.scrapper.service;

import edu.java.scrapper.model.Link;
import java.net.URI;
import java.util.List;
import java.util.Optional;

public interface LinkService {
    Optional<Link> add(long chatId, URI url);
    void remove(long chatId, URI url);
    List<Link> listAll(long chatId);
}
