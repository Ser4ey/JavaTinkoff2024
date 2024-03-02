package edu.java.bot.controller;

import edu.java.bot.model.dto.LinkUpdateRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/updates")
@AllArgsConstructor
public class UpdatesApiController {
    @PostMapping
    public ResponseEntity<Void> updateUrls(@RequestBody @Valid LinkUpdateRequest linkUpdateRequest) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}

