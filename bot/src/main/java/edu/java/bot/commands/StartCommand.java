package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
import edu.java.bot.states.State;
import lombok.NonNull;

public class StartCommand implements Command {
    public static final String WELCOME_MESSAGE = """
            Привет!
            Я бот для отслеживания обновлений.
            GitHub проекта: https://github.com/Ser4ey/JavaTinkoff2024""";

    @Override
    public @NonNull String getName() {
        return "/start";
    }

    @Override
    public @NonNull String getDescription() {
        return "Запустить бота";
    }

    @Override
    public CommandAnswer execute(ChatBotMessage chatMessage, State state) {
        return new CommandAnswer(WELCOME_MESSAGE, true);
    }
}

