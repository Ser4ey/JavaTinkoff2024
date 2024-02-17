package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.AbstractCommand;
import edu.java.bot.handlers.MainHandler;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@SuppressWarnings("all")
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

    public Long getChaiId(Update update) {
        return update.message().chat().id();
    }

    public String getMessageText(Update update) {
        return update.message().text();
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        bot.execute(sendMessage);
    }

    private void processUpdate(Update update) {
        if (update != null && update.message() != null) {
            commandHandler.handleCommand(this, update);
        }
    }

    private void setBotCommands() {
        List<BotCommand> commands = new ArrayList<>();
        for (AbstractCommand command : commandHandler.getAllCommands()) {
            commands.add(new BotCommand(command.getCommandName(), command.getCommandDescription()));
        }

        bot.execute(new SetMyCommands(commands.toArray(BotCommand[]::new)));
    }

    public void start() {
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::processUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

}

