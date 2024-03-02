package edu.java.scrapper.controller;

import edu.java.scrapper.model.dto.LinkResponse;
import edu.java.scrapper.model.dto.ListLinksResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tg-chat")
public class TgChatController {

    @PostMapping("/{id}")
    public ResponseEntity<ListLinksResponse> registerChat(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LinkResponse> deleteChat(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
