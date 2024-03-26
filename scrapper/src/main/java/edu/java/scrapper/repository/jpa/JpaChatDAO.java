package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.Chat;
import edu.java.scrapper.model.entity.ChatEntity;
import edu.java.scrapper.repository.ChatRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaChatDAO implements ChatRepository {
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinkRepository jpaLinkRepository;

    @Override
    public List<Chat> findAll() {
        return jpaChatRepository.findAll().stream()
            .map(ChatEntity::toChat)
            .collect(Collectors.toList());
    }

    @Override
    public List<Chat> findAllByLinkId(Integer linkId) {
        return null;
    }

    @Override
    public boolean isChatExist(Long uniqueChatId) {
        return jpaChatRepository.existsChatByChatId(uniqueChatId);
    }

    @Override
    public void add(Long chatId) {
        jpaChatRepository.save(
            new ChatEntity(chatId)
        );
    }

    @Override
    public void remove(Long chatId) {
        jpaChatRepository.delete(
            new ChatEntity(chatId)
        );
    }
}
