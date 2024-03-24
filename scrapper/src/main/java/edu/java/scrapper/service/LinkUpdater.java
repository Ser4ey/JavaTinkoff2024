package edu.java.scrapper.service;

public interface LinkUpdater {
    int update(int checkedLinksBatchSize); // возвращает кол-во обновлённых сылок
}
