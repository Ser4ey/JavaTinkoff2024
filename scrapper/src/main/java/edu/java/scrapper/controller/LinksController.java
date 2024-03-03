package edu.java.scrapper.controller;

import edu.java.scrapper.exception.CustomResponseException;
import edu.java.scrapper.exception.ResponseException404;
import edu.java.scrapper.exception.ResponseException409;
import edu.java.scrapper.model.dto.AddLinkRequest;
import edu.java.scrapper.model.dto.ApiErrorResponse;
import edu.java.scrapper.model.dto.LinkResponse;
import edu.java.scrapper.model.dto.ListLinksResponse;
import edu.java.scrapper.model.dto.RemoveLinkRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Links", description = "Links API")
@RestController
@RequestMapping("/links")
@SuppressWarnings("MagicNumber") // dev
public class LinksController {
    @GetMapping
    @Operation(summary = "Get all the tracked links", description = "Get all the tracked links")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ListLinksResponse.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        })
    })
    public ResponseEntity<ListLinksResponse> getAllLinks(@RequestHeader("Tg-Chat-Id") @Min(1) Long chatId) {
        LinkResponse link1 = new LinkResponse(100L, URI.create("https://github.com/Ser4ey/JavaTinkoff2024"));
        LinkResponse link2 = new LinkResponse(101L, URI.create("https://github.com/Ser4ey/JavaTinkoff2024/tree/hw3"));
        ListLinksResponse listLinksResponse = new ListLinksResponse(List.of(link1, link2), 2);

        return new ResponseEntity<>(listLinksResponse, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add tracking link", description = "Start tracking a new link")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        }),
        @ApiResponse(responseCode = "409", description = "The link is already registered", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        })
    })
    public ResponseEntity<LinkResponse> createLink(
        @RequestHeader("Tg-Chat-Id") @Min(1) Long chatId, @RequestBody @Valid AddLinkRequest addLinkRequest
    ) throws CustomResponseException {
        // тестовый вариант, вынесу логику в @Service
        if (chatId == 1) {
            throw new ResponseException409(
                "The link is already registered",
                "You cannot register a link 2 times in a row"
            );
        }

        LinkResponse linkResponse = new LinkResponse(993L, addLinkRequest.link());
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Remove link tracking", description = "The link will no longer be tracked")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The link was successfully deleted", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        }),
        @ApiResponse(responseCode = "404", description = "The link was not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        })
    })
    public ResponseEntity<LinkResponse> deleteLink(
        @RequestHeader("Tg-Chat-Id") @Min(1) Long chatId, @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) {
        if (chatId == 1) {
            throw new ResponseException404(
                "The chat was not found",
                "You can't delete something that doesn't exist"
            );
        }

        LinkResponse linkResponse = new LinkResponse(986L, removeLinkRequest.link());
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }
}
