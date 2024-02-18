package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.states.State;

public class HelpCommand extends AbstractCommand {
    public HelpCommand() {
        super("/help", "Список комманд");
    }

    private String buildHelpMessage() {
        StringBuilder helpText = new StringBuilder();
        for (RegisteredCommand registeredCommand : RegisteredCommand.values()) {
            AbstractCommand command = registeredCommand.getCommand();
            helpText.append(command.toString());
            helpText.append("\n");
        }
        return helpText.toString();
    }

    @Override
    public void execute(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChaiId(update);

        bot.sendMessage(chatId, buildHelpMessage());
    }
}
