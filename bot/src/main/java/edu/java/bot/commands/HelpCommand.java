package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
import edu.java.bot.states.State;
import java.util.List;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private final String helpText;

    @Override
    public int getOrder() {
        return 2;
    }

    public HelpCommand(@Autowired List<Command> commands) {
        commands.add(1, this);

        StringBuilder helpTextBuilder = new StringBuilder();
        for (Command command : commands) {
            helpTextBuilder.append(String.format("%s - %s\n", command.getName(), command.getDescription()));
        }
        this.helpText = helpTextBuilder.toString();
    }

    @Override
    public @NonNull String getName() {
        return "/help";
    }

    @Override
    public @NonNull String getDescription() {
        return "Список комманд";
    }

    private String buildHelpMessage() {
        return this.helpText;
    }

    @Override
    public CommandAnswer execute(ChatBotMessage chatMessage, State state) {
        return new CommandAnswer(buildHelpMessage(), false);
    }
}
