package edu.java.bot.handlers;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.RegisteredCommand;
import edu.java.bot.states.State;
import edu.java.bot.states.StateManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainHandler {
    private final Map<String, Command> commands = new HashMap<>();
    private final StateManager stateManager = new StateManager();
    public void registerCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public List<Command> getAllCommands() {
        return Arrays.stream(RegisteredCommand.values())
            .map(RegisteredCommand::getCommand)
            .collect(Collectors.toList());
    }

    public MainHandler() {
        for (RegisteredCommand registeredCommand : RegisteredCommand.values()) {
            Command command = registeredCommand.getCommand();
            registerCommand(command.getName(), command);
        }
    }

    @SuppressWarnings("ReturnCount")
    public void handleCommand(SimpleBot bot, Update update) {
        Long chatId = bot.getChatId(update);
        String messageText = bot.getMessageText(update);
        State currentChatState = stateManager.getState(chatId);

        // при отправки новой команды сбрасываем состояние
        if (Command.isCommand(messageText)) {
            currentChatState.clear();
            Command command = commands.get(messageText);
            if (command != null) {
                command.execute(bot, currentChatState, update);
            } else {
                unknownCommand(bot, update);
            }
            return;
        }

        // вызываем команду состояния, если она есть
        if (currentChatState.getCommand() != null) {
            currentChatState.getCommand().execute(bot, currentChatState, update);
            return;
        }

        currentChatState.clear();
        noCommand(bot, update);
    }

    private void unknownCommand(SimpleBot bot, Update update) {
        // действия при отправке неизвестной команды
        Long chatId = bot.getChatId(update);
        String text = bot.getMessageText(update);

        String message = String.format("Неизвестная команда: %s\nСписок команд: /help", text);
        bot.sendMessage(chatId, message);
    }

    private void noCommand(SimpleBot bot, Update update) {
        // действия при отправке текстка
        Long chatId = bot.getChatId(update);
        String text = bot.getMessageText(update);

        String message = String.format("Ваш id: %s\nВаш текст: %s", chatId, text);
        bot.sendMessage(chatId, message);
    }

}
