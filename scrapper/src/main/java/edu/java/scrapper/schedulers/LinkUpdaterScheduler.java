package edu.java.scrapper.schedulers;

import edu.java.scrapper.configuration.ApplicationConfig.Scheduler;
import edu.java.scrapper.service.LinkUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableScheduling
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.scheduler", name = "enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdaterScheduler {
    private final LinkUpdater linkUpdater;
    private final Scheduler scheduler;

    @Scheduled(fixedRateString = "#{@scheduler.interval().toMillis()}")
    public void update() {
        log.info("Обновляем ссылки");
        int numberOfUpdatedLinks = linkUpdater.update(scheduler.checkedLinksBatchSize());
        log.info("Обновлено ссылок: {}", numberOfUpdatedLinks);
    }
}
