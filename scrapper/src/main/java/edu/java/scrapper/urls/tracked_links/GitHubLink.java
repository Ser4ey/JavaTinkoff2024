package edu.java.scrapper.urls.tracked_links;

import edu.java.scrapper.client.GitHubClient;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

//@RequiredArgsConstructor
@Log4j2
@Component
@RequiredArgsConstructor
@SuppressWarnings({"ReturnCount", "MagicNumber"})
public class GitHubLink implements TrackedLink {
    private final GitHubClient gitHubClient;
    private static final String LINK_HOST = "github.com";

//    public GitHubLink(GitHubClient gitHubClient) {
//        this.gitHubClient = gitHubClient;
//
//        System.out.println(isWorkingUrl(
//            URI.create("https://github.com/Ser4ey/JavaTinkoff2024/pull/6")
//        ));
//    }
    @Override
    public boolean isCurrentLinkHost(URI url) {
        var linkHost = TrackedLink.getHostFromUrl(url);
        return linkHost.isPresent() && linkHost.get().equals(LINK_HOST);
    }

    @Override
    public boolean isWorkingUrl(URI url) {
        // https://github.com/Ser4ey/JavaTinkoff2024/pull/6
        if (!isCurrentLinkHost(url)) {
            return false;
        }

        String path = url.getPath();
        String[] parts = path.split("/");
        if (parts.length < 3) {
            return false;
        }

        String owner = parts[1];
        String repo = parts[2];

        try {
            var answer = gitHubClient.getRepository(owner, repo);
            log.info("Ответ: {}", answer);
            if (answer == null) {
                return false;
            }

        } catch (Exception e) {
            log.warn("Неизвестная ошибка при запросе к GitHub API", e);
            return false;
        }

        return true;
    }

    @Override
    public Optional<OffsetDateTime> getLastActivityTime(URI url) {
        return null;
    }
}
