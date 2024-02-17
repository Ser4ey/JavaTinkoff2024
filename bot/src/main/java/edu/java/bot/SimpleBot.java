package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.handlers.MainHandler;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class SimpleBot {
    private final TelegramBot bot;
    private final MainHandler commandHandler = new MainHandler();

    public SimpleBot(String botToken) {
        bot = new TelegramBot(botToken);
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
        commandHandler.handleCommand(this, update);
    }

    public void start() {
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::processUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public static void main(String[] args) {
        String botToken =  System.getenv("BOT_TOKEN");

        SimpleBot bot = new SimpleBot(botToken);
        bot.start();
        System.out.println("boot_started");
    }
}

