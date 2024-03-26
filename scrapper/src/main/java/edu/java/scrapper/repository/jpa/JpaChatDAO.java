package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.Chat;
import edu.java.scrapper.model.entity.ChatEntity;
import edu.java.scrapper.repository.ChatRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaChatDAO implements ChatRepository {
    private final JpaChatRepository jpaChatRepository;

    @Override
    public List<Chat> findAll() {
        var chats = jpaChatRepository.findAll();

        List<Chat> newChats = new ArrayList<>();
        for (var c : chats) {
            newChats.add(
                new Chat(c.getChatId())
            );
        }
        return newChats;
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
