package edu.java.scrapper.urls.tracked_links;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.dto.response.GitHubOwnerRepoResponse;
import edu.java.scrapper.urls.UrlUpdateDto;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@SuppressWarnings({"ReturnCount", "MagicNumber", "MultipleStringLiterals"})
public class GitHubLink implements TrackedLink {
    private final GitHubClient gitHubClient;
    private static final String LINK_HOST = "github.com";
    private static final String UNKNOWN_GITHUB_API_ERROR = "Неизвестная ошибка при запросе к GitHub API: {}";

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

        try {
            var ownerRepoPair = getOwnerAndRepo(url);
            if (ownerRepoPair.isEmpty()) {
                return false;
            }
            String owner = ownerRepoPair.get().getLeft();
            String repo = ownerRepoPair.get().getRight();

            var answer = gitHubClient.getRepository(owner, repo);
            log.info("Ответ от GitHub API: {}", answer);
            if (answer == null) {
                return false;
            }
        } catch (Exception e) {
            log.warn(UNKNOWN_GITHUB_API_ERROR, e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Optional<UrlUpdateDto> getUpdate(Link link) {
        try {
            URI url = link.url();

            var ownerRepoPair = getOwnerAndRepo(url);
            if (ownerRepoPair.isEmpty()) {
                log.warn("GitHub не удалось получить Owner/Repo: {}", url);
                return Optional.empty();
            }
            String owner = ownerRepoPair.get().getLeft();
            String repo = ownerRepoPair.get().getRight();

            var answer = gitHubClient.getRepository(owner, repo);
            log.info("Ответ от GitHub API: {}", answer);
            if (answer == null) {
                return Optional.empty();
            }

            return getUpdateDTO(link, answer);

        } catch (Exception e) {
            log.warn(UNKNOWN_GITHUB_API_ERROR, e.getMessage());
            return Optional.empty();
        }
    }

    private static Optional<UrlUpdateDto> getUpdateDTO(Link link, GitHubOwnerRepoResponse answer) {
        StringBuilder updateText = new StringBuilder();

        log.debug("Старое время: {} Новок время: {}", link.lastUpdateTime(), answer.updatedAt());
        if (
            link.lastUpdateTime().isEqual(answer.updatedAt())
            || link.lastUpdateTime().isAfter(answer.updatedAt())
        ) {
            log.info("Информация по ссылке не изменилась: {}", link.url().toString());
        } else {
            updateText.append("Информация по GitHub ссылке обновилась");
        }

        if (!Objects.equals(link.count(), answer.forksCount())) {
            if (!updateText.isEmpty()) {
                updateText.append("\n");
            }
            updateText.append(String.format("Изменилось кол-во forks: %d -> %d", link.count(), answer.forksCount()));
        }

        if (updateText.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(
            new UrlUpdateDto(
                updateText.toString(),
                answer.updatedAt(),
                answer.forksCount()
            )
        );
    }

    private static Optional<Pair<String, String>> getOwnerAndRepo(URI url) {
        String path = url.getPath();
        String[] parts = path.split("/");
        if (parts.length < 3) {
            return Optional.empty();
        }

        String owner = parts[1];
        String repo = parts[2];
        Pair<String, String> ownerRepo = Pair.of(owner, repo);
        return Optional.of(ownerRepo);
    }
}
