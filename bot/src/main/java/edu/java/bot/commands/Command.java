package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;
import java.util.regex.Pattern;
import lombok.NonNull;

public interface Command {
    static boolean isCommand(String text) {
        String regex = "^/[a-zA-Z]*$";
        return Pattern.matches(regex, text);
    }

    CommandAnswer execute(SimpleBot bot, State state, Update update);

    @NonNull
    String getName();

    @NonNull
    String getDescription();
}

