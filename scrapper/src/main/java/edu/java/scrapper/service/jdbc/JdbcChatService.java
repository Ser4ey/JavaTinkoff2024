package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.repository.ChatRepository;
import edu.java.scrapper.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Log4j2
@Service
public class JdbcChatService implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public void register(long chatId) {
        try {
            chatRepository.add(chatId);
        } catch (Exception e) {
            log.info("Чат с {} уже существует", chatId, e);
        }
    }

    @Override
    public void unregister(long chatId) {
        chatRepository.remove(chatId);
    }
}
