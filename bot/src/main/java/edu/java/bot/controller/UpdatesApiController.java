package edu.java.bot.controller;

import edu.java.bot.SimpleBot;
import edu.java.bot.model.dto.request.LinkUpdateRequest;
import edu.java.bot.model.dto.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Updates", description = "Updates API")
@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
@Log4j2
public class UpdatesApiController {
    private final SimpleBot simpleBot;

    @PostMapping
    @Operation(summary = "Send update",
               description = "Send information that the source has been updated so that the bot "
                   + "sends notifications to users.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The update has been processed"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        })
    })
    public void updateUrls(@RequestBody @Valid LinkUpdateRequest linkUpdateRequest) {
        log.info("Получено обновление {}", linkUpdateRequest);

        for (Long chatId: linkUpdateRequest.tgChatIds()) {
            simpleBot.sendMessageWithWebPagePreview(
                chatId,
                linkUpdateRequest.url().toString() + "\n\n" + linkUpdateRequest.description()
            );
        }
    }
}

