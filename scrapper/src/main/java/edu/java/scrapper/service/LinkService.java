package edu.java.scrapper.service;

import edu.java.scrapper.exception.service_exceptions.LinkAlreadyNotTracking;
import edu.java.scrapper.exception.service_exceptions.LinkAlreadyTracking;
import edu.java.scrapper.model.Link;
import java.net.URI;
import java.util.List;

public interface LinkService {
    Link add(long chatId, URI url) throws LinkAlreadyTracking;

    void remove(long chatId, URI url) throws LinkAlreadyNotTracking;

    List<Link> listAll(long chatId);
}
