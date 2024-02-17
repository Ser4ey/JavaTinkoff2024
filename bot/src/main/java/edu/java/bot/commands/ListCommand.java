package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;

public class ListCommand extends AbstractCommand {
    public ListCommand() {
        super("/list", "Список отслеживаемых ссылок");
    }

    @Override
    public void execute(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChaiId(update);
        bot.sendMessage(chatId, "command: list");
    }
}
