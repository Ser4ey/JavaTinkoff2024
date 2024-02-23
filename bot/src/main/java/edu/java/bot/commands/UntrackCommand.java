package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.SimpleBot;
import edu.java.bot.chatbot.ChatBotMessageInterface;
import edu.java.bot.db.LocalDBFactory;
import edu.java.bot.db.UserLinkDB;
import edu.java.bot.states.State;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SuppressWarnings("MemberName")
public class UntrackCommand implements Command {
    private final UserLinkDB db;
    public final static String STATUS_WAIT_URL = "statusWaitUrl";

    @Override
    public @NonNull String getName() {
        return "/untrack";
    }

    @Override
    public @NonNull String getDescription() {
        return "Удалить ссылку";
    }


    public UntrackCommand() {
        db = LocalDBFactory.getInstance();
    }

    @Override
    public CommandAnswer execute(ChatBotMessageInterface chatMessage, State state) {
        return switch (state.getStepName()) {
            case null -> noStatus(state);
            case STATUS_WAIT_URL -> statusWaitUrl(chatMessage, state);
            default -> {
                log.error("Неизвестный статус: " + state.getStepName());
                yield noStatus(state);
            }
        };
    }

    public CommandAnswer noStatus(State state) {
        state.setStepName(STATUS_WAIT_URL);
        state.setCommand(this);

        return new CommandAnswer("Введите ссылку для удаления:", false);
    }

    public CommandAnswer statusWaitUrl(ChatBotMessageInterface chatMessage, State state) {
        Long chatId = chatMessage.getChatId();
        String url = chatMessage.getMessageText();

        String answerText;
        if (checkUrlAlreadyInDB(chatId, url)) {
            delUrlFromDB(chatId, url);
            answerText = "Ссылка успешно удалена!";
        } else {
            answerText = "Ссылка не отслеживается!";
        }

        state.clear();
        return new CommandAnswer(answerText, false);
    }

    public void delUrlFromDB(Long chatId, String url) {
        db.delUserLinks(chatId, url);
    }

    public boolean checkUrlAlreadyInDB(Long chatId, String url) {
        return db.checkUserLink(chatId, url);
    }
}
