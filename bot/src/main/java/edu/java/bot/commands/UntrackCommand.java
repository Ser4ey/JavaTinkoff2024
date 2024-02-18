package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.db.LocalDBFactory;
import edu.java.bot.db.UserLinkDB;
import edu.java.bot.states.State;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SuppressWarnings("MemberName")
public class UntrackCommand extends AbstractCommand {
    public UntrackCommand() {
        super("/untrack", "Удалить ссылку");
    }

    public final static String STATUS_WAIT_URL = "statusWaitUrl";

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
                log.error("Неизвестный статус: " + state.getStepName());
                noStatus(bot, state, update);
        }
    }

    public void noStatus(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChatId(update);
        bot.sendMessage(chatId, "Введите ссылку для удаления:");

        state.setStepName(STATUS_WAIT_URL);
        state.setCommand(this);
    }

    public void statusWaitUrl(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChatId(update);
        String url = bot.getMessageText(update);

        if (checkUrlAlreadyInDB(chatId, url)) {
            delUrlFromDB(chatId, url);
            bot.sendMessage(chatId, "Ссылка успешно удалена!");
        } else {
            bot.sendMessage(chatId, "Ссылка не отслеживается!");
        }

        state.clear();
    }

    public void delUrlFromDB(Long chatId, String url) {
        UserLinkDB db = LocalDBFactory.getInstance();
        db.delUserLinks(chatId, url);
    }

    public boolean checkUrlAlreadyInDB(Long chatId, String url) {
        UserLinkDB db = LocalDBFactory.getInstance();
        return db.checkUserLink(chatId, url);
    }
}
