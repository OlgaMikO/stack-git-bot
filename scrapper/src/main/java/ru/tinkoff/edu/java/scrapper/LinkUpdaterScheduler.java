package ru.tinkoff.edu.java.scrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@Configuration
public class LinkUpdaterScheduler {

    @Scheduled(fixedDelayString = "#{@getInterval}")
    public void update(){
        log.info("Обновление данных");
    }

}
