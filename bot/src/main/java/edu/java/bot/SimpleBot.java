package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.chatbot.TelegramBotMessage;
import edu.java.bot.commands.AllCommands;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.CommandAnswer;
import edu.java.bot.handlers.MainHandler;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SimpleBot {
    private final TelegramBot bot;
    private final MainHandler commandHandler = new MainHandler();

    public SimpleBot(@Value("${app.telegram-token}") String botToken) {
        bot = new TelegramBot(botToken);
        setBotCommands();
        start();
        log.info("BOT started!");
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text).disableWebPagePreview(true);
        bot.execute(sendMessage);
    }

    public void sendMessageWithWebPagePreview(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        bot.execute(sendMessage);
    }

    public void sendMessage(Long chatId, CommandAnswer commandAnswer) {
        if (commandAnswer.isWithPagePreview()) {
            sendMessageWithWebPagePreview(chatId, commandAnswer.getAnswerText());
        } else {
            sendMessage(chatId, commandAnswer.getAnswerText());
        }
    }

    private void processUpdate(Update update) {
        if (update != null && update.message() != null) {
            Long chatId = TelegramUtils.getChatId(update);
            String messageText = TelegramUtils.getMessageText(update);

            TelegramBotMessage telegramMessage = new TelegramBotMessage(chatId, messageText);
            CommandAnswer commandAnswer = commandHandler.handleCommand(telegramMessage);

            sendMessage(chatId, commandAnswer);
        }
    }

    private void setBotCommands() {
        List<BotCommand> commands = new ArrayList<>();
        for (Command command : AllCommands.getAllCommands()) {
            commands.add(new BotCommand(command.getName(), command.getDescription()));
        }

        bot.execute(new SetMyCommands(commands.toArray(BotCommand[]::new)));
    }

    private void start() {
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::processUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public static class TelegramUtils {
        private TelegramUtils() {}

        public static Long getChatId(Update update) {
            return update.message().chat().id();
        }

        public static String getMessageText(Update update) {
            return update.message().text();
        }
    }

}

