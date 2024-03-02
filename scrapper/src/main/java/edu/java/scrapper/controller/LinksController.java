package edu.java.scrapper.controller;

import edu.java.scrapper.model.dto.AddLinkRequest;
import edu.java.scrapper.model.dto.LinkResponse;
import edu.java.scrapper.model.dto.ListLinksResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/links")
public class LinksController {
    @GetMapping("")
    public ResponseEntity<ListLinksResponse> getAllLinks(@RequestHeader("Tg-Chat-Id") Long chatId) {
        ListLinksResponse listLinksResponse = new ListLinksResponse(null, 1);
        return new ResponseEntity<>(listLinksResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<LinkResponse> createLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody @Valid AddLinkRequest addLinkRequest
    ) {
        LinkResponse linkResponse = new LinkResponse(1L, null);
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<LinkResponse> deleteLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody @Valid AddLinkRequest addLinkRequest
    ) {
        LinkResponse linkResponse = new LinkResponse(1L, null);
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }
}
