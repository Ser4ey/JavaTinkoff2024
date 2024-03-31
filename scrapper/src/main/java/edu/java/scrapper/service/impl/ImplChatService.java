package edu.java.scrapper.service.impl;

import edu.java.scrapper.exception.service.ChatAlreadyRegistered;
import edu.java.scrapper.exception.service.ChatNotFound;
import edu.java.scrapper.model.Chat;
import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Log4j2
@Service
public class ImplChatService implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public void register(long chatId) {
        try {
            chatRepository.add(chatId);
        } catch (Exception e) {
            throw new ChatAlreadyRegistered();
        }
    }

    @Override
    public void unregister(long chatId) {
        if (!chatRepository.isChatExist(chatId)) {
            throw new ChatNotFound("You can't delete something that doesn't exist");
        }

        chatRepository.remove(chatId);
    }

    @Override
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public List<Chat> findAllByLinkId(Integer linkId) {
        return chatRepository.findAllByLinkId(linkId);
    }
}
