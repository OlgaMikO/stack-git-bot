package ru.tinkoff.edu.java.scrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdaterImpl;

@Slf4j
@EnableScheduling
@Configuration
public class LinkUpdaterScheduler {

    LinkUpdaterImpl linkUpdater = new LinkUpdaterImpl();

    @Scheduled(fixedDelayString = "#{@getInterval}")
    public void update(){
        log.info(String.format("Обновление данных - %d", linkUpdater.update()));
    }

}
