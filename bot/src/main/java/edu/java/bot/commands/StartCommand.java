package edu.java.bot.commands;

import edu.java.bot.chatbot.ChatBotMessage;
import edu.java.bot.exception.service.ScrapperException;
import edu.java.bot.service.ScrapperService;
import edu.java.bot.states.State;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class StartCommand implements Command {
    public static final String WELCOME_MESSAGE = """
            Привет!
            Я бот для отслеживания обновлений.
            GitHub проекта: https://github.com/Ser4ey/JavaTinkoff2024""";

    private final ScrapperService scrapperService;

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public @NonNull String getName() {
        return "/start";
    }

    @Override
    public @NonNull String getDescription() {
        return "Запустить бота";
    }

    @Override
    public CommandAnswer execute(ChatBotMessage chatMessage, State state) {
        try {
            scrapperService.registerChat(chatMessage.getChatId());
        } catch (ScrapperException e) {
            log.debug("Command /start. Code: {} Описание: {} Текст ошибки: {}",
                e.getStatusCode(), e.getDescription(), e.getExceptionMessage());

            if (e.getStatusCode().equals("0")) {
                var answerText = String.format("""
                Сервер не доступен
                Описание: %s
                Ошибка: %s
                """, e.getDescription(), e.getExceptionMessage());
                return new CommandAnswer(answerText, false);
            }
        }
        return new CommandAnswer(WELCOME_MESSAGE, true);

    }
}

