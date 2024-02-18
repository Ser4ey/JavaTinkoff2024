package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.db.LocalDBFactory;
import edu.java.bot.db.UserLinkDB;
import edu.java.bot.states.State;
import edu.java.bot.urls.RegisteredUrl;
import edu.java.bot.urls.UrlWorker;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SuppressWarnings("MemberName")
public class TrackCommand extends AbstractCommand {
    public TrackCommand() {
        super("/track", "Добавить ссылку");
    }

    public static final String STATUS_WAIT_URL = "statusWaitUrl";

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
        bot.sendMessage(chatId, "Введите ссылку для отслеживания:");

        state.setStepName(STATUS_WAIT_URL);
        state.setCommand(this);
    }

    public void statusWaitUrl(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChatId(update);
        String url = bot.getMessageText(update);

        if (checkUrlAlreadyInDB(chatId, url)) {
            bot.sendMessage(chatId, "Ссылка уже отслеживается");
            state.clear();
            return;
        }

        if (!checkUrlValid(url)) {
            bot.sendMessage(chatId, "Ссылка не валидна");
            state.clear();
            return;
        }

        if (!isUrlRegistered(url)) {
            bot.sendMessage(
                chatId, String.format("Сайт %s не отслеживается!\n Введите другую ссылку", getLinkHost(url))
            );
            state.clear();
            return;
        }

        bot.sendMessage(chatId, "Ссылка успешно добавлена!");
        addUrlToDB(chatId, url);
        state.clear();
    }

    public void addUrlToDB(Long chatId, String url) {
        UserLinkDB db = LocalDBFactory.getInstance();
        db.addUserLinks(chatId, url);
    }

    public boolean checkUrlAlreadyInDB(Long chatId, String url) {
        UserLinkDB db = LocalDBFactory.getInstance();
        return db.checkUserLink(chatId, url);
    }

    private boolean checkUrlValid(String url) {
        return UrlWorker.isValidUrl(url);
    }

    private boolean isUrlRegistered(String url) {
        return RegisteredUrl.getRegisteredUrl(url) != null;
    }

    private String getLinkHost(String url) {
        return UrlWorker.getHostFromUrl(url);
    }

}
