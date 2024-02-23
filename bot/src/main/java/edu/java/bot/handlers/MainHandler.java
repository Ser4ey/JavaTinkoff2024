package edu.java.bot.handlers;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.commands.AllCommands;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.CommandAnswer;
import edu.java.bot.states.State;
import edu.java.bot.states.StateManager;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class MainHandler {
    private final StateManager stateManager = new StateManager();

    @SuppressWarnings("ReturnCount")
    public CommandAnswer handleCommand(SimpleBot bot, Update update) {
        Long chatId = bot.getChatId(update);
        String messageText = bot.getMessageText(update);
        State currentChatState = stateManager.getState(chatId);

        // при отправки новой команды сбрасываем состояние
        if (Command.isCommand(messageText)) {
            currentChatState.clear();
            Command command = AllCommands.getCommand(messageText);
            if (command != null) {
                return command.execute(bot, currentChatState, update);
            } else {
                return unknownCommand(bot, update);
            }
        }

        // вызываем команду состояния, если она есть
        if (currentChatState.getCommand() != null) {
            return currentChatState.getCommand().execute(bot, currentChatState, update);
        }

        currentChatState.clear();
        return noCommand(bot, update);
    }

    private CommandAnswer unknownCommand(SimpleBot bot, Update update) {
        // действия при отправке неизвестной команды
        Long chatId = bot.getChatId(update);
        String text = bot.getMessageText(update);

        String message = String.format("Неизвестная команда: %s\nСписок команд: /help", text);
        return new CommandAnswer(message, false);
    }

    private CommandAnswer noCommand(SimpleBot bot, Update update) {
        // действия при отправке текстка
        Long chatId = bot.getChatId(update);
        String text = bot.getMessageText(update);

        String message = String.format("Ваш id: %s\nВаш текст: %s", chatId, text);
        return new CommandAnswer(message, false);
    }

}
