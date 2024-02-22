package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;
import lombok.NonNull;

public class StartCommand implements Command {
    public static final String WELCOME_MESSAGE = "Привет!\nЯ бот для отслеживания обновлений.\nСписок команд: /help";

    @Override
    public @NonNull String getName() {
        return "/start";
    }

    @Override
    public @NonNull String getDescription() {
        return "Запустить бота";
    }

    @Override
    public void execute(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChatId(update);
        bot.sendMessage(chatId, WELCOME_MESSAGE);
    }
}

