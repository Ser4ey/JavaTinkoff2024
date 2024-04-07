package edu.java.bot.service.impl;

import edu.java.bot.SimpleBot;
import edu.java.bot.model.dto.request.LinkUpdateRequest;
import edu.java.bot.service.UpdateUrlsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUrlsServiceImpl implements UpdateUrlsService {

    private final SimpleBot simpleBot;

    @Override
    public void update(LinkUpdateRequest linkUpdateRequest) {
        for (Long chatId: linkUpdateRequest.tgChatIds()) {
            simpleBot.sendMessageWithWebPagePreview(
                chatId,
                linkUpdateRequest.url().toString() + "\n\n" + linkUpdateRequest.description()
            );
        }
    }
}
