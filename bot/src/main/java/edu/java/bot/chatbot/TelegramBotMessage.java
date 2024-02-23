package edu.java.bot.chatbot;

import com.pengrad.telegrambot.model.Update;
import lombok.NonNull;

public class TelegramBotMessage implements ChatBotMessageInterface {
    private final Long chatId;
    private final String messageText;

    public TelegramBotMessage(@NonNull Update update) {
        if (update.message() != null) {
            chatId = update.message().chat().id();
            messageText = update.message().text();
        } else {
            chatId = 0L;
            messageText = "";
        }
    }

    @Override
    public Long getChatId() {
        return chatId;
    }

    @Override
    public String getMessageText() {
        return messageText;
    }
}
