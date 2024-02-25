package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class StackOverflowWebClientTest {
    public WireMockServer wireMockServer;
    private StackOverflowWebClient client;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);

        client = new StackOverflowWebClient("http://localhost:8080");
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void testGetQuestion_Success200() {
        long id = 5901452;
        String getUrl = "/questions/" + id + "?order=desc&sort=activity&site=stackoverflow";

        String wireMockAnswer =
            "{\"items\":[{\"tags\":[\"java\",\"performance\",\"scala\",\"memory\"],\"owner\":{\"account_id\":116282,\"reputation\":4618,\"user_id\":304969,\"user_type\":\"registered\",\"accept_rate\":79,\"profile_image\":\"https://www.gravatar.com/avatar/b6a5cf8e57de90b6f9f3863a0aba294f?s=256&d=identicon&r=PG\",\"display_name\":\"JohnSmith\",\"link\":\"https://stackoverflow.com/users/304969/johnsmith\"},\"is_answered\":true,\"view_count\":90938,\"closed_date\":1353962192,\"accepted_answer_id\":5901682,\"answer_count\":8,\"score\":164,\"last_activity_date\":1649578568,\"creation_date\":1304614626,\"question_id\":5901452,\"link\":\"https://stackoverflow.com/questions/5901452/scala-vs-java-performance-and-memory\",\"closed_reason\":\"not constructive\",\"title\":\"scala vs java, performance and memory?\"}],\"has_more\":false,\"quota_max\":300,\"quota_remaining\":248}";

        stubFor(WireMock.get(getUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(wireMockAnswer)));

        var answer = client.getQuestion(id);

        assertNotNull(answer);
        assertEquals(answer.id(), id);
        assertEquals(answer.title(), "scala vs java, performance and memory?");
        assertEquals(answer.lastActivityDate(), OffsetDateTime.parse("2022-04-10T08:16:08Z"));
    }

    @Test
    public void testGetQuestion_Success200ByNoQuestion() {
        long id = -1;
        String getUrl = "/questions/" + id + "?order=desc&sort=activity&site=stackoverflow";

        String wireMockAnswer = "{\"items\":[],\"has_more\":false,\"quota_max\":300,\"quota_remaining\":247}";

        stubFor(WireMock.get(getUrl)
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-type", "application/json")
                .withBody(wireMockAnswer)));

        var answer = client.getQuestion(id);

        assertNull(answer);
    }

    @Test
    public void testGetQuestion_Error400() {
        long id = 5901452;
        String getUrl = "/questions/" + id + "?order=desc&sort=activity&site=stackoverflow1";

        String wireMockAnswer =
            "{\"error_id\":400,\"error_message\":\"No site found for name `stackoverflow1`\",\"error_name\":\"bad_parameter\"}";

        stubFor(WireMock.get(getUrl)
            .willReturn(aResponse()
                .withStatus(400)
                .withHeader("Content-type", "application/json")
                .withBody(wireMockAnswer)));

        var answer = client.getQuestion(id);
        assertNull(answer);
    }

}
