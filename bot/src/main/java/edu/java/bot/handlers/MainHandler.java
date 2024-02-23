package edu.java.bot.handlers;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.chatbot.ChatBotMessageInterface;
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
    public CommandAnswer handleCommand(ChatBotMessageInterface chatMessage) {
        Long chatId = chatMessage.getChatId();
        String messageText = chatMessage.getMessageText();
        State currentChatState = stateManager.getState(chatId);

        // при отправки новой команды сбрасываем состояние
        if (Command.isCommand(messageText)) {
            currentChatState.clear();
            Command command = AllCommands.getCommand(messageText);
            if (command != null) {
                return command.execute(chatMessage, currentChatState);
            } else {
                return unknownCommand(chatMessage);
            }
        }

        // вызываем команду состояния, если она есть
        if (currentChatState.getCommand() != null) {
            return currentChatState.getCommand().execute(chatMessage, currentChatState);
        }

        currentChatState.clear();
        return noCommand(chatMessage);
    }

    private CommandAnswer unknownCommand(ChatBotMessageInterface chatMessage) {
        // действия при отправке неизвестной команды
        String text = chatMessage.getMessageText();

        String message = String.format("Неизвестная команда: %s\nСписок команд: /help", text);
        return new CommandAnswer(message, false);
    }

    private CommandAnswer noCommand(ChatBotMessageInterface chatMessage) {
        // действия при отправке текстка
        Long chatId = chatMessage.getChatId();
        String text = chatMessage.getMessageText();

        String message = String.format("Ваш id: %s\nВаш текст: %s", chatId, text);
        return new CommandAnswer(message, false);
    }

}
