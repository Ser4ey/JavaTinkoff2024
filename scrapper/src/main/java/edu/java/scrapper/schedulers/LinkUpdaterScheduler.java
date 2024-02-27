package edu.java.scrapper.schedulers;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableScheduling
@ConditionalOnProperty(prefix = "app.scheduler", name = "enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdaterScheduler {
    @Scheduled(fixedRateString = "#{@scheduler.interval().toMillis()}")
    public void update() {
        log.info("Scheduler by {}", "https://github.com/Ser4ey/");
    }
}
