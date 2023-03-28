package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundScrapperException;

import java.util.ArrayList;

@RestController
public class ScrapperApiController {

    public ArrayList<Long> exampleID = new ArrayList<>();

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<?> registerChat(@PathVariable("id") Long id){
        if(!exampleID.contains(id)){
            exampleID.add(id);
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(200))
                    .body("Чат зарегистрирован");
        }
        throw new BadRequestException("Некорректные параметры запроса");
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable("id") Long id){
        if(exampleID.isEmpty()){
            throw new BadRequestException("Некорректные параметры запроса");
        }
        if(!exampleID.contains(id)){
            throw new NotFoundScrapperException("Чат не существует");
        }
        else {
            exampleID.remove(id);
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(200))
                    .body("Чат успешно удалён");
        }
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader("Tg-Chat-Id") Long id){

        if(!exampleID.isEmpty()){
            ArrayList<LinkResponse> links = new ArrayList<>();
            for(long ex: exampleID){
                links.add(new LinkResponse(ex, "something"));
            }
            return ResponseEntity.status(200).body(new ListLinksResponse(links, (long)links.size()));
        }
        throw new BadRequestException("Некорректные параметры запроса");
    }

    @PostMapping("/links")
    public LinkResponse addLinkTracking(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody AddLinkRequest link){
        if(exampleID.contains(id)){
            return new LinkResponse(id, link.getLink());
        }
        throw new BadRequestException("Некорректные параметры запроса");
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> deleteLinkTracking(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody RemoveLinkRequest link){
        if(exampleID.isEmpty()){
            throw new BadRequestException("Некорректные параметры запроса");
        }
        if(exampleID.contains(id)){
            return ResponseEntity.ok(new LinkResponse(id, link.getLink()));
        }
        else{
            throw new NotFoundScrapperException("Ссылка не найдена");
        }

    }

}
