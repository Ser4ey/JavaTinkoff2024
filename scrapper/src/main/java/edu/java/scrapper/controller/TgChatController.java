package edu.java.scrapper.controller;

import edu.java.scrapper.model.dto.response.ApiErrorResponse;
import edu.java.scrapper.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Telegram Chat", description = "Telegram Chat API")
@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class TgChatController {
    private final ChatService chatService;

    @PostMapping("/{id}")
    @Operation(summary = "Register a chat", description = "Register a new chat with a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chat registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        }),
        @ApiResponse(responseCode = "409", description = "The chat is already registered", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        })
    })
    public void registerChat(@PathVariable @Min(1) Long id) {
        chatService.register(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a chat", description = "Delete registered chat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chat registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        }),
        @ApiResponse(responseCode = "404", description = "The chat was not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        })
    })
    public void deleteChat(@PathVariable @Min(1) Long id) {
        chatService.unregister(id);
    }

}
