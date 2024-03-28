package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
import edu.java.bot.exception.service.ScrapperException;
import edu.java.bot.states.State;
import edu.java.bot.urls.AllUrls;
import edu.java.bot.urls.UrlWorker;
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
public class TrackCommand implements Command {
    public static final String STATUS_WAIT_URL = "statusWaitUrl";

    private final ScrapperService scrapperService;

    @Override
    public int getOrder() {
        return 4;
    }

    @Override
    public @NonNull String getName() {
        return "/track";
    }

    @Override
    public @NonNull String getDescription() {
        return "Добавить ссылку";
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
        return new CommandAnswer("Введите ссылку для отслеживания:", false);
    }

    @SuppressWarnings("ReturnCount")
    public CommandAnswer statusWaitUrl(ChatBotMessage chatMessage, State state) {
        Long chatId = chatMessage.getChatId();
        String url = chatMessage.getMessageText();

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

        try {
            addUrl(chatId, url);
            state.clear();
            return new CommandAnswer("Ссылка успешно добавлена!", false);
        } catch (ScrapperException e) {
            state.clear();
            log.debug("Не далось получить список ссылок. Code: {} Описание: {} Текст ошибки: {}",
                e.getStatusCode(), e.getDescription(), e.getDescription());
            var answerText = String.format("""
                Не удалось добавить ссылку
                Описание: %s
                Ошибка: %s
                """, e.getDescription(), e.getExceptionMessage());
            return new CommandAnswer(answerText, false);
        }
    }

    public void addUrl(Long chatId, String url) {
        scrapperService.addLink(chatId, URI.create(url));
    }
}
