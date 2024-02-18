package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;

public class StartCommand extends AbstractCommand {
    public static final String WELCOME_MESSAGE = "Привет!\nЯ бот для отслеживания обновлений.\nСписок команд: /help";

    public StartCommand() {
        super("/start", "Запустить бота");
    }

    @Override
    public void execute(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChatId(update);
        bot.sendMessage(chatId, WELCOME_MESSAGE);
    }

}
