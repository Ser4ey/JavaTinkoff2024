package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.db.LocalDBFactory;
import edu.java.bot.db.UserLinkDB;
import edu.java.bot.states.State;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SuppressWarnings("MemberName")
public class TrackCommand extends AbstractCommand {
    public TrackCommand() {
        super("/track", "Добавить ссылку");
    }

    private final String STATUS_WAIT_URL = "statusWaitUrl";

    @Override
    public void execute(SimpleBot bot, State state, Update update) {
        switch (state.getStepName()) {
            case null:
                noStatus(bot, state, update);
                break;
            case STATUS_WAIT_URL:
                statusWaitUrl(bot, state, update);
                break;
            default:
                log.warn("Неизвестный статус: " + state.getStepName());
                noStatus(bot, state, update);
        }
    }

    public void noStatus(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChatId(update);
        bot.sendMessage(chatId, "Введите ссылку для отслеживания:");

        state.setStepName(STATUS_WAIT_URL);
        state.setCommand(this);
    }

    public void statusWaitUrl(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChatId(update);
        String url = bot.getMessageText(update);

        // тут будет проверка
        if (url.contains("http")) {
            bot.sendMessage(chatId, "Ссылка успешно добавлена!");
            UserLinkDB db = LocalDBFactory.getInstance();
            db.addUserLinks(chatId, url);
        } else {
            bot.sendMessage(chatId, "Некорректный формат ссылки!");
        }

        state.clear();
    }


}
