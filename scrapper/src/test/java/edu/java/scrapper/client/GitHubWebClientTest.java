package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@WireMockTest(httpPort = 8080)
public class GitHubWebClientTest {
    private final GitHubWebClient client = new GitHubWebClient("http://localhost:8080");

    @Test
    public void testGetRepository_Success200() {
        String owner = "Ser4ey";
        String repo = "JavaTinkoff2024";
        String getUrl = "/repos/" + owner + "/" + repo;

        String wireMockAnswer =
            "{\"id\":757232225,\"node_id\":\"R_kgDOLSJyYQ\",\"name\":\"JavaTinkoff2024\",\"full_name\":\"Ser4ey/JavaTinkoff2024\",\"private\":false,\"owner\":{\"login\":\"Ser4ey\",\"id\":51812894,\"node_id\":\"MDQ6VXNlcjUxODEyODk0\",\"avatar_url\":\"https://avatars.githubusercontent.com/u/51812894?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/Ser4ey\",\"html_url\":\"https://github.com/Ser4ey\",\"followers_url\":\"https://api.github.com/users/Ser4ey/followers\",\"following_url\":\"https://api.github.com/users/Ser4ey/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/Ser4ey/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/Ser4ey/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/Ser4ey/subscriptions\",\"organizations_url\":\"https://api.github.com/users/Ser4ey/orgs\",\"repos_url\":\"https://api.github.com/users/Ser4ey/repos\",\"events_url\":\"https://api.github.com/users/Ser4ey/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/Ser4ey/received_events\",\"type\":\"User\",\"site_admin\":false},\"html_url\":\"https://github.com/Ser4ey/JavaTinkoff2024\",\"description\":null,\"fork\":false,\"url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024\",\"forks_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/forks\",\"keys_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/teams\",\"hooks_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/hooks\",\"issue_events_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/events\",\"assignees_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/tags\",\"blobs_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/languages\",\"stargazers_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/stargazers\",\"contributors_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/contributors\",\"subscribers_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/subscribers\",\"subscription_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/subscription\",\"commits_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/merges\",\"archive_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/downloads\",\"issues_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/Ser4ey/JavaTinkoff2024/deployments\",\"created_at\":\"2024-02-14T04:07:47Z\",\"updated_at\":\"2024-02-18T10:50:39Z\",\"pushed_at\":\"2024-02-25T05:58:29Z\",\"git_url\":\"git://github.com/Ser4ey/JavaTinkoff2024.git\",\"ssh_url\":\"git@github.com:Ser4ey/JavaTinkoff2024.git\",\"clone_url\":\"https://github.com/Ser4ey/JavaTinkoff2024.git\",\"svn_url\":\"https://github.com/Ser4ey/JavaTinkoff2024\",\"homepage\":null,\"size\":186,\"stargazers_count\":1,\"watchers_count\":1,\"language\":\"Java\",\"has_issues\":true,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":true,\"has_pages\":false,\"has_discussions\":false,\"forks_count\":0,\"mirror_url\":null,\"archived\":false,\"disabled\":false,\"open_issues_count\":2,\"license\":null,\"allow_forking\":true,\"is_template\":false,\"web_commit_signoff_required\":false,\"topics\":[],\"visibility\":\"public\",\"forks\":0,\"open_issues\":2,\"watchers\":1,\"default_branch\":\"master\",\"temp_clone_token\":null,\"network_count\":0,\"subscribers_count\":1}";

        stubFor(WireMock.get(getUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(wireMockAnswer)));

        var answer = client.getRepository(owner, repo);

        assertNotNull(answer);
        assertEquals(answer.id(), 757232225L);
        assertEquals(answer.name(), "JavaTinkoff2024");
        assertEquals(answer.pushedAt(), OffsetDateTime.parse("2024-02-25T05:58:29Z"));
        assertEquals(answer.updatedAt(), OffsetDateTime.parse("2024-02-18T10:50:39Z"));
    }

    @Test
    public void testGetRepository_NotFound404() {
        String owner = "Ser4ey";
        String repo = "JavaTinkoff2024";
        String getUrl = "/repos/" + owner + "/" + repo;

        String wireMockAnswer =
            "{\"message\":\"Not Found\",\"documentation_url\":\"https://docs.github.com/rest/repos/repos#get-a-repository\"}";

        stubFor(WireMock.get(getUrl)
            .willReturn(aResponse()
                .withStatus(404)
                .withHeader("Content-type", "application/json")
                .withBody(wireMockAnswer)));

        var answer = client.getRepository(owner, repo);

        assertNull(answer);
    }

    @Test
    void testGetRepository_Error414() {
        String owner = "Ser4ey";
        String repo = "JavaTinkoff2024";
        String getUrl = "/repos/" + owner + "/" + repo;

        String wireMockAnswer = "{\"message\": \"We received a Request-URL that is too long from your client.\"}\n";

        stubFor(WireMock.get(getUrl)
            .willReturn(aResponse()
                .withStatus(414)
                .withHeader("Content-type", "application/json")
                .withBody(wireMockAnswer)));

        var answer = client.getRepository(owner, repo);

        assertNull(answer);
    }

    @Test
    public void testGetRepository_Error500() {
        String owner = "Ser4ey";
        String repo = "JavaTinkoff2024";
        String getUrl = "/repos/" + owner + "/" + repo;

        String wireMockAnswer = "{}";

        stubFor(WireMock.get(getUrl)
            .willReturn(aResponse()
                .withStatus(500)
                .withHeader("Content-type", "application/json")
                .withBody(wireMockAnswer)));

        var answer = client.getRepository(owner, repo);

        assertNull(answer);
    }

}
