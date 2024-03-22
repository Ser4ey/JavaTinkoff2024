package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.exception.service_exceptions.ChatAlreadyRegistered;
import edu.java.scrapper.exception.service_exceptions.ChatNotFound;
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
public class JdbcChatService implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public void register(long chatId) throws ChatAlreadyRegistered {
        try {
            chatRepository.add(chatId);
        } catch (Exception e) {
            throw new ChatAlreadyRegistered();
        }
    }

    @Override
    public void unregister(long chatId) throws ChatNotFound {
        if (!chatRepository.isChatExist(chatId)) {
            throw new ChatNotFound();
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
