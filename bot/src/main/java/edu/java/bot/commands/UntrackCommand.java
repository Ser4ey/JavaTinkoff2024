package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;

public class UntrackCommand extends AbstractCommand {
    public UntrackCommand() {
        super("/untrack", "Удалить ссылку");
    }

    @Override
    public void execute(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChaiId(update);
        bot.sendMessage(chatId, "command: untrack");
    }
}
