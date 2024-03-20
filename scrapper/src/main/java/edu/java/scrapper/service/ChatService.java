package edu.java.scrapper.service;

import edu.java.scrapper.exception.service_exceptions.ChatAlreadyRegistered;
import edu.java.scrapper.exception.service_exceptions.ChatNotFound;
import edu.java.scrapper.model.Chat;
import java.util.List;

public interface ChatService {
    void register(long chatId) throws ChatAlreadyRegistered;

    void unregister(long chatId) throws ChatNotFound;

    List<Chat> findAll();

    List<Chat> findAll(Integer linkId);
}
