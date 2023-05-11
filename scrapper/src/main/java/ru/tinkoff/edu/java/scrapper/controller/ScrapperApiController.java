package ru.tinkoff.edu.java.scrapper.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.service.database.LinkService;
import ru.tinkoff.edu.java.scrapper.service.database.TgChatService;

@RestController
@RequiredArgsConstructor
public class ScrapperApiController {

    private final LinkService linkService;

    private final TgChatService tgChatService;

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<?> registerChat(@PathVariable("id") Long id) {
        tgChatService.register(id);
        return ResponseEntity
            .ok()
            .body("Чат зарегистрирован");
//        throw new BadRequestException("Некорректные параметры запроса");
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable("id") Long id) {
        tgChatService.unregister(id);
        return ResponseEntity
            .ok()
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
        return ResponseEntity.ok().body(new ListLinksResponse(links, (long) links.size()));
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
    public ResponseEntity<LinkResponse> deleteLinkTracking(
        @RequestHeader("Tg-Chat-Id") Long id,
        @RequestBody RemoveLinkRequest link
    ) {
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
