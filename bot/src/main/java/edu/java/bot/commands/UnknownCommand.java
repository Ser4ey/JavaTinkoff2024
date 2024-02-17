package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;

public class UnknownCommand implements Command {
    @Override
    public void execute(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChaiId(update);
        String text = bot.getMessageText(update);

        String message = String.format("Неизвестная команда: %s\nСписок команд: /help", text);
        bot.sendMessage(chatId, message);
    }
}
