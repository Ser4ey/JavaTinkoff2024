package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.service.LinkUpdater;
import java.util.Collections;
import java.util.List;

public class LinkUpdaterImpl implements LinkUpdater {
    @Override
    public List<Link> update() {
        // возвращает кол-во обновлённых сылок
        return Collections.emptyList();
    }
}
