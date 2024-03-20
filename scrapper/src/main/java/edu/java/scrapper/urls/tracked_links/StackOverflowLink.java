package edu.java.scrapper.urls.tracked_links;

import edu.java.scrapper.client.StackOverflowClient;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@SuppressWarnings({"ReturnCount", "MagicNumber"})
public class StackOverflowLink implements TrackedLink {

    private final StackOverflowClient stackOverflowClient;
    private static final String LINK_HOST = "stackoverflow.com";
    private static final String UNKNOWN_STACKOVERFLOW_API_ERROR = "Неизвестная ошибка при запросе к StackOverflow API";

//    public StackOverflowLink(StackOverflowClient stackOverflowClient) {
//        this.stackOverflowClient = stackOverflowClient;
//        System.out.println("------------------------");
//
//        System.out.println(
//            isWorkingUrl(
//                URI.create("https://stackoverflow.com/questions/45784119/cannot-instantiate-the-type-pair-although-not-abstract")
//            )
//        );
//        System.out.println(
//            getLastActivityTime(
//                URI.create("https://stackoverflow.com/questions/45784119/cannot-instantiate-the-type-pair-although-not-abstract")
//            )
//        );
//
//        System.out.println("------------------------");
//    }

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
            log.warn(UNKNOWN_STACKOVERFLOW_API_ERROR, e);
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
            if (answer == null) {
                return Optional.empty();
            }

            return Optional.ofNullable(answer.lastActivityDate());

        } catch (Exception e) {
            log.warn(UNKNOWN_STACKOVERFLOW_API_ERROR, e);
            return Optional.empty();
        }

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
