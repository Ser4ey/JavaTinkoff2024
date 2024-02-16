package edu.java.bot.commands;

import java.util.regex.Pattern;
import com.pengrad.telegrambot.model.Update;



@FunctionalInterface
public interface Command {
    static boolean isCommand(String text) {
        String regex = "^/[a-zA-Z]*$";
        return Pattern.matches(regex, text);
    }

    void execute(Update update);
}
