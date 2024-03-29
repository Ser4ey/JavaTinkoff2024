package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.Chat;
import edu.java.scrapper.model.entity.ChatEntity;
import edu.java.scrapper.model.entity.EntityMapper;
import edu.java.scrapper.repository.ChatRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JpaChatDAO implements ChatRepository {
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinkRepository jpaLinkRepository;

    private List<Chat> convertListOfChatEntityToListOfChat(List<ChatEntity> listOfChatEntity) {
        var listOfChats = new ArrayList<Chat>();
        for (ChatEntity chatEntity : listOfChatEntity) {
            listOfChats.add(EntityMapper.toChat(chatEntity));
        }
        return listOfChats;
    }

    @Override
    public List<Chat> findAll() {
        return convertListOfChatEntityToListOfChat(jpaChatRepository.findAll());
    }

    @Override
    @Transactional
    public List<Chat> findAllByLinkId(Integer linkId) {
        var linkEntity = jpaLinkRepository.findById(linkId);
        if (linkEntity.isEmpty()) {
            return new ArrayList<>();
        }

        return convertListOfChatEntityToListOfChat(linkEntity.get().getChats());
    }

    @Override
    public boolean isChatExist(Long uniqueChatId) {
        return jpaChatRepository.existsChatByChatId(uniqueChatId);
    }

    @Override
    @Transactional
    public void add(Long chatId) {
        if (isChatExist(chatId)) {
            throw new IllegalArgumentException("Чат с таким ID уже существует");
        }

        jpaChatRepository.save(
            new ChatEntity(chatId)
        );
    }

    @Override
    @Transactional
    public void remove(Long chatId) {
        jpaChatRepository.deleteById(chatId);
    }
}
