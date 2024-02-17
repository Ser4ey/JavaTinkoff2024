package edu.java.bot.handlers;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.commands.AbstractCommand;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.NoCommand;
import edu.java.bot.commands.RegisteredCommand;
import edu.java.bot.commands.UnknownCommand;
import edu.java.bot.states.State;
import edu.java.bot.states.StateManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainHandler {
    private final Map<String, Command> commands = new HashMap<>();
    private final StateManager stateManager = new StateManager();
    private final Command noCommand = new NoCommand();
    private final Command unknownCommand = new UnknownCommand();

    public void registerCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public MainHandler() {
        for (RegisteredCommand registeredCommand : RegisteredCommand.values()){
            AbstractCommand command = registeredCommand.getCommand();
            registerCommand(command.getCommandName(), command);
        }
    }

    public void handleCommand(SimpleBot bot, Update update) {
        Long chatId = bot.getChaiId(update);
        String messageText = bot.getMessageText(update);

        State cerrentChatState = stateManager.getState(chatId);

        if (Command.isCommand(messageText)){
            Command command = commands.get(messageText);
            Objects.requireNonNullElse(command, unknownCommand).execute(bot, cerrentChatState, update);
        } else {
            noCommand.execute(bot, cerrentChatState, update);
        }

    }
}
