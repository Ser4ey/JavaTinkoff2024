package edu.java.scrapper.schedulers;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class LinkUpdaterScheduler {
//    @Scheduled(fixedRateString = "${app.scheduler.interval}")
    @Scheduled(fixedRateString = "#{@scheduler.interval().toMillis()}")
    public void update() {
        log.info("Scheduler by {}", "https://github.com/Ser4ey/");
    }
}
