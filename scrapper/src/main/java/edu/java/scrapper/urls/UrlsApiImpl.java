package edu.java.scrapper.urls;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UrlsApiImpl implements UrlsApi {
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    @Autowired
    public UrlsApiImpl(GitHubClient gitHubClient, StackOverflowClient stackOverflowClient) {
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;

//        System.out.println("12232123432");
//        System.out.println(gitHubClient);
    }

    private static final Set<String> URL_HOSTS = new LinkedHashSet<>();

    static {
        URL_HOSTS.add("github.com");
        URL_HOSTS.add("stackoverflow.com");
    }


//    public static String getRegisteredUrl(String url) {
//        // если ссылка зарегистрирована получаем её хост, в противном случае null
//        boolean isValid = UrlWorker.isValidUrl(url);
//        if (!isValid) {
//            return null;
//        }
//
//        String urlHost = UrlWorker.getHostFromUrl(url);
//        if (URL_HOSTS.contains(urlHost)) {
//            return urlHost;
//        }
//        return null;
//    }

//    public static boolean isAllowedUrl(String url) {
//        return getRegisteredUrl(url) != null;
//    }

    @Override
    public boolean isWorkingUrl(URI url) {
        return false;
    }

    @Override
    public OffsetDateTime getLastActivity(URI url) {
        return null;
    }

}

