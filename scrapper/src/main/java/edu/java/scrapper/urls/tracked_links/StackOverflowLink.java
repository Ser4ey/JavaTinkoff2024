package edu.java.scrapper.urls.tracked_links;

import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.dto.response.StackOverflowQuestionsResponse;
import edu.java.scrapper.urls.UrlUpdateDto;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@SuppressWarnings({"ReturnCount", "MagicNumber", "MultipleStringLiterals"})
public class StackOverflowLink implements TrackedLink {

    private final StackOverflowClient stackOverflowClient;
    private static final String LINK_HOST = "stackoverflow.com";
    private static final String UNKNOWN_STACKOVERFLOW_API_ERROR =
        "Неизвестная ошибка при запросе к StackOverflow API: {}";

    @Override
    public boolean isCurrentLinkHost(URI url) {
        var linkHost = TrackedLink.getHostFromUrl(url);
        return linkHost.isPresent() && linkHost.get().equals(LINK_HOST);
    }

    @Override
    public boolean isWorkingUrl(URI url) {
        if (!isCurrentLinkHost(url)) {
            return false;
        }

        var questionId = getQuestionId(url);
        if (questionId.isEmpty()) {
            return false;
        }

        try {
            var answer = stackOverflowClient.getQuestion(questionId.get());
            log.info("Ответ от StackOverflow API: {}", answer);
            if (answer == null) {
                return false;
            }

        } catch (Exception e) {
            log.warn(UNKNOWN_STACKOVERFLOW_API_ERROR, e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Optional<OffsetDateTime> getLastActivityTime(URI url) {
        try {
            var questionId = getQuestionId(url);
            if (questionId.isEmpty()) {
                return Optional.empty();
            }

            var answer = stackOverflowClient.getQuestion(questionId.get());
            log.info("Ответ от StackOverflow API: {}", answer);
            if (answer == null) {
                return Optional.empty();
            }

            return Optional.ofNullable(answer.lastActivityDate());

        } catch (Exception e) {
            log.warn(UNKNOWN_STACKOVERFLOW_API_ERROR, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<UrlUpdateDto> getUpdate(Link link) {
        try {
            URI url = link.url();
            var questionId = getQuestionId(url);
            if (questionId.isEmpty()) {
                log.warn("StackOverflow не удалось получить QuestionId: {}", url);
                return Optional.empty();
            }

            var answer = stackOverflowClient.getQuestion(questionId.get());
            log.info("Ответ от StackOverflow API: {}", answer);
            if (answer == null) {
                return Optional.empty();
            }

            return getUpdateDTO(link, answer);

        } catch (Exception e) {
            log.warn(UNKNOWN_STACKOVERFLOW_API_ERROR, e.getMessage());
            return Optional.empty();
        }
    }

    private static Optional<UrlUpdateDto> getUpdateDTO(
        Link link,
        StackOverflowQuestionsResponse.StackOverflowQuestion answer
    ) {
        log.debug("Старое время: {} Новок время: {}", link.lastUpdateTime(), answer.lastActivityDate());
        if (
            link.lastUpdateTime().isEqual(answer.lastActivityDate())
            || link.lastUpdateTime().isAfter(answer.lastActivityDate())
        ) {
            log.info("Информация по ссылке не изменилась: {}", link.url().toString());
            return Optional.empty();
        }

        StringBuilder updateText = new StringBuilder();
        updateText.append("Информация по StackOverflow ссылке обновилась");
        if (!Objects.equals(link.count(), answer.answersCount())) {
            updateText.append(String.format("\nКол-во ответов: %d -> %d", link.count(), answer.answersCount()));
        }
        log.debug(updateText);

        return Optional.of(
            new UrlUpdateDto(
                updateText.toString(),
                answer.lastActivityDate(),
                answer.answersCount()
            )
        );
    }

    private static Optional<Long> getQuestionId(URI url) {
        String path = url.getPath();
        String[] parts = path.split("/");
        if (parts.length < 3) {
            return Optional.empty();
        }

        try {
            Long id = Long.parseLong(parts[2]);
            return Optional.of(id);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

}
