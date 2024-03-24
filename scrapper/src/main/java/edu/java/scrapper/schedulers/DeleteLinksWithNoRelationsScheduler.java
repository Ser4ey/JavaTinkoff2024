package edu.java.scrapper.schedulers;

import edu.java.scrapper.service.LinkService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Log4j2
@Component
@EnableScheduling
@RequiredArgsConstructor
public class DeleteLinksWithNoRelationsScheduler {
    private final LinkService linkService;

    @PostConstruct
    public void init() {
        deleteLinksWithNoRelations();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteLinksWithNoRelations() {
        log.info("Удаляем ссылки без связей");
        linkService.removeLinksWithNoRelations();
        log.info("Все ссылки без связей удалены");
    }
}
