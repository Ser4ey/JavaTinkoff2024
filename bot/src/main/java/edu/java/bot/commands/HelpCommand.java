package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;

public class HelpCommand extends AbstractCommand {
    public HelpCommand() {
        super("/help", "Помощь");
    }

    @Override
    public void execute(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChaiId(update);
        bot.sendMessage(chatId, "command: help");
    }
}
