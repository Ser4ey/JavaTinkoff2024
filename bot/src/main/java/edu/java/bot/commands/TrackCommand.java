package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.db.LocalDBFactory;
import edu.java.bot.db.UserLinkDB;
import edu.java.bot.states.State;
import edu.java.bot.urls.AllUrls;
import edu.java.bot.urls.UrlWorker;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SuppressWarnings("MemberName")
public class TrackCommand implements Command {
    public static final String STATUS_WAIT_URL = "statusWaitUrl";
    private final UserLinkDB db;

    @Override
    public @NonNull String getName() {
        return "/track";
    }

    @Override
    public @NonNull String getDescription() {
        return "Добавить ссылку";
    }

    public TrackCommand() {
        db = LocalDBFactory.getInstance();
    }

    @Override
    public CommandAnswer execute(SimpleBot bot, State state, Update update) {
        return switch (state.getStepName()) {
            case null -> noStatus(bot, state, update);
            case STATUS_WAIT_URL -> statusWaitUrl(bot, state, update);
            default -> {
                log.error("Неизвестный статус: " + state.getStepName());
                yield noStatus(bot, state, update);
            }
        };
    }

    public CommandAnswer noStatus(SimpleBot bot, State state, Update update) {
        state.setStepName(STATUS_WAIT_URL);
        state.setCommand(this);
        return new CommandAnswer("Введите ссылку для отслеживания:", false);
    }

    @SuppressWarnings("ReturnCount")
    public CommandAnswer statusWaitUrl(SimpleBot bot, State state, Update update) {
        Long chatId = bot.getChatId(update);
        String url = bot.getMessageText(update);

        if (!UrlWorker.isValidUrl(url)) {
            state.clear();
            return new CommandAnswer("Ссылка не валидна", false);
        }

        if (!AllUrls.isAllowedUrl(url)) {
            state.clear();
            return new CommandAnswer(
                String.format("Сайт %s не отслеживается!", UrlWorker.getHostFromUrl(url)),
                false
            );
        }

        if (checkUrlAlreadyInDB(chatId, url)) {
            state.clear();
            return new CommandAnswer("Ссылка уже отслеживается", false);
        }

        addUrlToDB(chatId, url);
        state.clear();
        return new CommandAnswer("Ссылка успешно добавлена!", false);

    }

    public void addUrlToDB(Long chatId, String url) {
        db.addUserLinks(chatId, url);
    }

    public boolean checkUrlAlreadyInDB(Long chatId, String url) {
        return db.checkUserLink(chatId, url);
    }

}

