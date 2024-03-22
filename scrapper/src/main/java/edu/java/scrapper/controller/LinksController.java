package edu.java.scrapper.controller;

import edu.java.scrapper.exception.request_response_exceptions.CustomResponseException;
import edu.java.scrapper.exception.request_response_exceptions.ResponseException400;
import edu.java.scrapper.exception.request_response_exceptions.ResponseException404;
import edu.java.scrapper.exception.request_response_exceptions.ResponseException409;
import edu.java.scrapper.exception.service_exceptions.LinkAlreadyTracking;
import edu.java.scrapper.exception.service_exceptions.LinkNotFound;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.dto.AddLinkRequest;
import edu.java.scrapper.model.dto.ApiErrorResponse;
import edu.java.scrapper.model.dto.LinkResponse;
import edu.java.scrapper.model.dto.ListLinksResponse;
import edu.java.scrapper.model.dto.RemoveLinkRequest;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.urls.UrlsApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class LinksController {
    private final LinkService linkService;

    private final UrlsApi urlsApi;

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
    public ListLinksResponse getAllLinks(@RequestHeader("Tg-Chat-Id") @Min(1) Long chatId) {

        List<Link> links = linkService.listAllByChatId(chatId);

        List<LinkResponse> linkResponseList = new LinkedList<>();

        for (Link link : links) {
            LinkResponse linkResponse = new LinkResponse(
                link.id().longValue(),
                link.url()
            );
            linkResponseList.add(linkResponse);
        }

        return new ListLinksResponse(linkResponseList, linkResponseList.size());
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
    public LinkResponse createLink(
        @RequestHeader("Tg-Chat-Id") @Min(1) Long chatId, @RequestBody @Valid AddLinkRequest addLinkRequest
    ) throws CustomResponseException {
        boolean isWorkingUrl = urlsApi.isWorkingUrl(addLinkRequest.link());
        if (!isWorkingUrl) {
            throw new ResponseException400(
                "The link didn't pass the work check",
                "The link is not available for updating via the API. Check that the link is correct."
            );
        }

        try {
            var link = linkService.add(chatId, addLinkRequest.link());

            return new LinkResponse(link.id().longValue(), link.url());
        } catch (LinkAlreadyTracking e) {
            throw new ResponseException409(
                "The link is already registered",
                "You cannot register a link 2 times in a row"
            );
        }
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
    public LinkResponse deleteLink(
        @RequestHeader("Tg-Chat-Id") @Min(1) Long chatId, @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) {
        try {
            linkService.remove(chatId, removeLinkRequest.link());

            return new LinkResponse(0L, removeLinkRequest.link());
        } catch (LinkNotFound e) {
            throw new ResponseException404(
                "The link is not tracked. You can't delete something that doesn't exist.",
                "You can't delete something that doesn't exist"
            );
        }
    }

}

