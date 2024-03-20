package edu.java.scrapper.service;

import edu.java.scrapper.exception.service_exceptions.ChatAlreadyRegistered;
import edu.java.scrapper.exception.service_exceptions.ChatNotFound;

public interface ChatService {
    void register(long chatId) throws ChatAlreadyRegistered;

    void unregister(long chatId) throws ChatNotFound;
}
