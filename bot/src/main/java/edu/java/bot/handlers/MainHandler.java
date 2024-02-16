package edu.java.bot.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.NoCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.states.State;
import edu.java.bot.states.StateManager;

public class MainHandler {
    private final Map<String, Command> commands = new HashMap<>();
    private final StateManager stateManager = new StateManager();
    private final Command noCommand = new NoCommand();

    public void registerCommand(String commandName, Command command){
        commands.put(commandName, command);
    }

    public MainHandler() {
        registerCommand("/start", new StartCommand());
        registerCommand("/help", new HelpCommand());
    }

    public void handleCommand(SimpleBot bot, Update update) {
        Long chatId = bot.getChaiId(update);
        String messageText = bot.getMessageText(update);

        State cerrentChatState = stateManager.getState(chatId);

        Command command = commands.get(messageText);
        Objects.requireNonNullElse(command, noCommand).execute(bot, cerrentChatState, update);

    }
}
