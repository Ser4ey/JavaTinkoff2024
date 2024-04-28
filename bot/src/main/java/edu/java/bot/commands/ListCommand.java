package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
import edu.java.bot.exception.service.ScrapperException;
import edu.java.bot.service.ScrapperService;
import edu.java.bot.states.State;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@SuppressWarnings("MagicNumber")
@Log4j2
public class ListCommand implements Command {
    public static final String NO_LINKS = "Нет отслеживаемых ссылок!";

    private final ScrapperService scrapperService;

    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public @NonNull String getName() {
        return "/list";
    }

    @Override
    public @NonNull String getDescription() {
        return "Список отслеживаемых ссылок";
    }

    @Override
    public CommandAnswer execute(ChatBotMessage chatMessage, State state) {
        Long chatId = chatMessage.getChatId();

        try {
            List<String> links = getUserLinks(chatId);
            if (links.isEmpty()) {
                return new CommandAnswer(NO_LINKS, false);
            }
            return new CommandAnswer(buildListMessage(links), false);
        } catch (ScrapperException e) {
            log.debug("Не далось получить список ссылок. Code: {} Описание: {} Текст ошибки: {}",
                e.getStatusCode(), e.getDescription(), e.getExceptionMessage());
            var answerText = String.format("""
                Не удалось получить список ссылок
                Описание: %s
                Ошибка: %s
                """, e.getDescription(), e.getExceptionMessage());
            return new CommandAnswer(answerText, false);
        }
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
        return scrapperService.getUserLinks(chatId);
    }
}

