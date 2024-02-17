package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.db.LocalDBFactory;
import edu.java.bot.db.UserLinkDB;
import edu.java.bot.states.State;
import java.util.List;

public class ListCommand extends AbstractCommand {
    public ListCommand() {
        super("/list", "Список отслеживаемых ссылок");
    }

    @Override
    public void execute(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChaiId(update);
        UserLinkDB db = LocalDBFactory.getInstance();

        List<String> links = db.getUserLinks(chatId);
        if (links.isEmpty()) {
            bot.sendMessage(chatId, "Нет ссылок!");
            return;
        }

        StringBuilder linksText = new StringBuilder();
        for (String link : links) {
            linksText.append(link);
            linksText.append("\n\n");
        }
        bot.sendMessage(chatId, linksText.toString());

    }

}
