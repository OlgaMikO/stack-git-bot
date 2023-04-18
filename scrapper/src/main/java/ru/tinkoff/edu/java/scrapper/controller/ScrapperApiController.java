package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcTgChatService;

import java.net.URI;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
public class ScrapperApiController {

    @Autowired
    @Qualifier("getLinkService")
    private JdbcLinkService linkService;

    @Autowired
    @Qualifier("getTgChatService")
    private JdbcTgChatService tgChatService;

//    public ScrapperApiController(LinkService linkService, TgChatService tgChatService){
//        this.linkService = linkService;
//        this.tgChatService = tgChatService;
//    }

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<?> registerChat(@PathVariable("id") Long id) {
        tgChatService.register(id);
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body("Чат зарегистрирован");
//        throw new BadRequestException("Некорректные параметры запроса");
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable("id") Long id) {
        tgChatService.unregister(id);
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body("Чат успешно удалён");
//        if(exampleID.isEmpty()){
//            throw new BadRequestException("Некорректные параметры запроса");
//        }
//        if(!exampleID.contains(id)){
//            throw new NotFoundScrapperException("Чат не существует");
//        }
//        else {
//            exampleID.remove(id);
//            return ResponseEntity
//                    .status(HttpStatusCode.valueOf(200))
//                    .body("Чат успешно удалён");
//        }
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader("Tg-Chat-Id") Long id) {
        ArrayList<LinkResponse> links = linkService.listAll(id)
                .stream()
                .map(u -> new LinkResponse(u.getId(), u.getUrl().toString()))
                .collect(Collectors.toCollection(ArrayList::new));
        return ResponseEntity.status(200).body(new ListLinksResponse(links, (long) links.size()));
//        if(!exampleID.isEmpty()){
//            ArrayList<LinkResponse> links = new ArrayList<>();
//            for(long ex: exampleID){
//                links.add(new LinkResponse(ex, "something"));
//            }
//            return ResponseEntity.status(200).body(new ListLinksResponse(links, (long)links.size()));
//        }
//        throw new BadRequestException("Некорректные параметры запроса");
    }

    @PostMapping("/links")
    public LinkResponse addLinkTracking(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody AddLinkRequest link) {
        Link addLink = linkService.add(id, URI.create(link.link()));
        return new LinkResponse(addLink.getId(), addLink.getUrl().toString());
//        if (exampleID.contains(id)) {
//            return new LinkResponse(id, link.getLink());
//        }
//        throw new BadRequestException("Некорректные параметры запроса");
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> deleteLinkTracking(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody RemoveLinkRequest link) {
        Link removeLink = linkService.remove(id, URI.create(link.link()));
        return ResponseEntity.ok(new LinkResponse(removeLink.getId(), removeLink.getUrl().toString()));
//        if (exampleID.isEmpty()) {
//            throw new BadRequestException("Некорректные параметры запроса");
//        }
//        if (exampleID.contains(id)) {
//            return ResponseEntity.ok(new LinkResponse(id, link.getLink()));
//        } else {
//            throw new NotFoundScrapperException("Ссылка не найдена");
//        }
    }

}
