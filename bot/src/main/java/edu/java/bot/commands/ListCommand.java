package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
import edu.java.bot.db.LocalDBFactory;
import edu.java.bot.db.UserLinkDB;
import edu.java.bot.states.State;
import java.util.List;
import lombok.NonNull;

public class ListCommand implements Command {
    public static final String NO_LINKS = "Нет отслеживаемых ссылок!";
    private final UserLinkDB db;

    @Override
    public @NonNull String getName() {
        return "/list";
    }

    @Override
    public @NonNull String getDescription() {
        return "Список отслеживаемых ссылок";
    }

    public ListCommand() {
        db = LocalDBFactory.getInstance();
    }

    @Override
    public CommandAnswer execute(ChatBotMessage chatMessage, State state) {
        Long chatId = chatMessage.getChatId();

        List<String> links = getUserLinks(chatId);
        if (links.isEmpty()) {
            return new CommandAnswer(NO_LINKS, false);
        }

        return new CommandAnswer(buildListMessage(links), false);
    }

    private String buildListMessage(List<String> links) {
        StringBuilder listText = new StringBuilder();
        listText.append("Список ссылок:\n\n");

        for (String link : links) {
            listText.append(link).append("\n\n");
        }

        return listText.toString();
    }

    public List<String> getUserLinks(Long chatId) {
        return db.getUserLinks(chatId);
    }
}

