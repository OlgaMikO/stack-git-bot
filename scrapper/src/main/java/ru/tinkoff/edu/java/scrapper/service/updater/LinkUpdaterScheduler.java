package ru.tinkoff.edu.java.scrapper.service.updater;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.tinkoff.edu.java.scrapper.service.updater.LinkUpdaterImpl;

@Slf4j
@EnableScheduling
@Configuration
public class LinkUpdaterScheduler {

    private final LinkUpdaterImpl linkUpdater;

    @Autowired
    public LinkUpdaterScheduler(LinkUpdaterImpl linkUpdater) {
        this.linkUpdater = linkUpdater;
    }

    @Scheduled(fixedDelayString = "#{@getInterval}")
    public void update(){
        linkUpdater.update();
        //log.info(String.format("Обновление данных\n%s", ));
        log.info("Обновление данных\n");
    }

}
