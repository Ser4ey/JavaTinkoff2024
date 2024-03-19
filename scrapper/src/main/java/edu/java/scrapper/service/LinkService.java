package edu.java.scrapper.service;

import edu.java.scrapper.model.Link;
import java.net.URI;
import java.util.List;

public interface LinkService {
    Link add(long chatId, URI url);

    void remove(long chatId, URI url);

    List<Link> listAll(long chatId);
}
