package edu.java.scrapper.urls.tracked_links;

import edu.java.scrapper.client.GitHubClient;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@SuppressWarnings({"ReturnCount", "MagicNumber"})
public class GitHubLink implements TrackedLink {
    private final GitHubClient gitHubClient;
    private static final String LINK_HOST = "github.com";
    private static final String UNKNOWN_GITHUB_API_ERROR = "Неизвестная ошибка при запросе к GitHub API";

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
            log.warn(UNKNOWN_GITHUB_API_ERROR, e);
            return false;
        }

        return true;
    }

    @Override
    public Optional<OffsetDateTime> getLastActivityTime(URI url) {
        try {
            var ownerRepoPair = getOwnerAndRepo(url);
            if (ownerRepoPair.isEmpty()) {
                return Optional.empty();
            }
            String owner = ownerRepoPair.get().getLeft();
            String repo = ownerRepoPair.get().getRight();


            var answer = gitHubClient.getRepository(owner, repo);
            if (answer == null) {
                return Optional.empty();
            }
            return Optional.ofNullable(answer.updatedAt());

        } catch (Exception e) {
            log.warn(UNKNOWN_GITHUB_API_ERROR, e);
            return Optional.empty();
        }
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
