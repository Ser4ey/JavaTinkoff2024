package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.db.LocalDBFactory;
import edu.java.bot.db.UserLinkDB;
import edu.java.bot.states.State;
import java.util.List;

public class ListCommand extends AbstractCommand {
    public static final String NO_LINKS = "Нет отслеживаемых ссылок!";

    public ListCommand() {
        super("/list", "Список отслеживаемых ссылок");
    }

    @Override
    public void execute(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChaiId(update);

        List<String> links = getUserLinks(chatId);
        if (links.isEmpty()) {
            bot.sendMessage(chatId, NO_LINKS);
            return;
        }

        bot.sendMessage(chatId, buildListMessage(links));
    }

    private String buildListMessage(List<String> links) {
        StringBuilder listText = new StringBuilder();
        listText.append("Список ссылок:\n\n");

        for (String link : links) {
            listText.append(link);
            listText.append("\n\n");
        }

        return listText.toString();
    }

    public List<String> getUserLinks(Long chatId) {
        UserLinkDB db = LocalDBFactory.getInstance();
        return db.getUserLinks(chatId);
    }
}

