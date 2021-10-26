package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    @Test
    void shouldReturn404ForUnknownRequestTarget() throws IOException {
        HttpServer server = new HttpServer(10001);
        HttpClient httpClient = new HttpClient("localhost", 10001, "/non-existing" );
        assertEquals(404, httpClient.getStatusCode());
    }

    @Test
    void shouldRespondWithRequestTargetIn404() throws IOException {
        HttpServer server = new HttpServer(10002);
        HttpClient httpClient = new HttpClient("localhost", 10002, "/non-existing" );
        assertEquals("File not found: /non-existing", httpClient.getMessageBody());
    }

    @Test
    void shouldRespondWith200ForUnknownRequestTarget() throws IOException {
        HttpServer server = new HttpServer(10003);
        HttpClient client = new HttpClient("localhost", server.getPort(), "/hello");
        assertAll(
                () -> assertEquals(200, client.getStatusCode()),
                () -> assertEquals("Hello world", client.getMessageBody())
        );
    }
}