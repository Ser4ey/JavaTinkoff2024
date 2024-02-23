package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;
import lombok.NonNull;

public class HelpCommand implements Command {
    @Override
    public @NonNull String getName() {
        return "/help";
    }

    @Override
    public @NonNull String getDescription() {
        return "Список комманд";
    }

    private String buildHelpMessage() {
        StringBuilder helpText = new StringBuilder();
        for (Command command : AllCommands.getAllCommands()) {
            helpText.append(String.format("%s - %s", command.getName(), command.getDescription())).append("\n");
        }
        return helpText.toString();
    }

    @Override
    public CommandAnswer execute(SimpleBot bot, State state, Update update) {
        return new CommandAnswer(buildHelpMessage(), false);
    }

}
