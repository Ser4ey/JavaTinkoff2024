package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
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
            helpText.append(String.format("%s - %s\n", command.getName(), command.getDescription()));
        }
        return helpText.toString();
    }

    @Override
    public CommandAnswer execute(ChatBotMessage chatMessage, State state) {
        return new CommandAnswer(buildHelpMessage(), false);
    }

}
