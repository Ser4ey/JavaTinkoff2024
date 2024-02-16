package edu.java.bot.commands;

import java.util.regex.Pattern;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;

@FunctionalInterface
public interface Command {
    static boolean isCommand(String text) {
        String regex = "^/[a-zA-Z]*$";
        return Pattern.matches(regex, text);
    }

    void execute(SimpleBot bot, State state, Update update);
}
