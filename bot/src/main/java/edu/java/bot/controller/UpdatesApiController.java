package edu.java.bot.controller;

import edu.java.bot.model.dto.ApiErrorResponse;
import edu.java.bot.model.dto.LinkUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Updates", description = "Updates API")
@RestController
@RequestMapping("/updates")
@AllArgsConstructor
public class UpdatesApiController {
    @PostMapping
    @Operation(summary = "Send update",
               description = "Send information that the source has been updated so that the bot sends notifications to users.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The update has been processed"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        })
    })
    public ResponseEntity<Void> updateUrls(@RequestBody @Valid LinkUpdateRequest linkUpdateRequest) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

