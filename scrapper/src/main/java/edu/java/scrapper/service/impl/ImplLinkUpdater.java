package edu.java.scrapper.service.impl;

import edu.java.scrapper.service.LinkUpdater;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ImplLinkUpdater implements LinkUpdater {
    @Override
    public int update() {
        // возвращает кол-во обновлённых сылок
        log.info("Проверено {} ссылок. Из них обновлены: {}", 0, 0);

        return 0;
    }
}
