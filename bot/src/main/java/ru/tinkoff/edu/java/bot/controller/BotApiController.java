package ru.tinkoff.edu.java.bot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.Bot;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.exception.BadRequestException;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class BotApiController {

    public ArrayList<Long> exampleID = new ArrayList<>(Arrays.asList(0L, 1L));

    private final Bot bot;

    @PostMapping("/updates")
    public ResponseEntity<?> linkUpdate(@RequestBody @Valid LinkUpdateRequest request) {
        if(exampleID.contains(request.getId())){
            bot.execute(new SendMessage(request.getId(), String.format("Обновление ссылки: %s", request.getUrl())));
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(200))
                    .body("Обновление обработано");
        }
        throw new BadRequestException("Некорректные параметры запроса");
    }

}
