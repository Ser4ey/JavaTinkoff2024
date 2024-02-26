package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
import edu.java.bot.states.State;
import java.util.regex.Pattern;
import lombok.NonNull;

public interface Command {
    static boolean isCommand(String text) {
        String regex = "^/[a-zA-Z]*$";
        return Pattern.matches(regex, text);
    }

    CommandAnswer execute(ChatBotMessage chatMessage, State state);

    @NonNull
    String getName();

    @NonNull
    String getDescription();
}

