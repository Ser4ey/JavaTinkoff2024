package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
import edu.java.bot.exception.service.ScrapperException;
import edu.java.bot.states.State;
import java.net.URI;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import service.ScrapperService;

@Component
@Log4j2
@RequiredArgsConstructor
@SuppressWarnings({"MemberName", "MagicNumber"})
public class UntrackCommand implements Command {
    private final ScrapperService scrapperService;
    public final static String STATUS_WAIT_URL = "statusWaitUrl";

    @Override
    public int getOrder() {
        return 5;
    }

    @Override
    public @NonNull String getName() {
        return "/untrack";
    }

    @Override
    public @NonNull String getDescription() {
        return "Удалить ссылку";
    }

    @Override
    public CommandAnswer execute(ChatBotMessage chatMessage, State state) {
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

    public CommandAnswer statusWaitUrl(ChatBotMessage chatMessage, State state) {
        Long chatId = chatMessage.getChatId();
        String url = chatMessage.getMessageText();

        try {
            delUrl(chatId, url);
            state.clear();
            return new CommandAnswer("Ссылка успешно удалена!", false);
        } catch (ScrapperException e) {
            state.clear();
            log.debug("Не далось получить список ссылок. Code: {} Описание: {} Текст ошибки: {}",
                e.getStatusCode(), e.getDescription(), e.getDescription());
            var answerText = String.format("""
                Не удалось удалить ссылку
                Описание: %s
                Ошибка: %s
                """, e.getDescription(), e.getExceptionMessage());
            return new CommandAnswer(answerText, false);
        }
    }

    public void delUrl(Long chatId, String url) {
        scrapperService.removeLink(chatId, URI.create(url));
    }
}
