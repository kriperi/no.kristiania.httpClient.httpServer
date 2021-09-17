package no.kristiania.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientTest {
    @Test
    void test () {
        assertEquals(200, 100 + 100);
    }

    @Test
    void shouldReturnStatusCode () {
        assertEquals(404, new HttpClient("httpbin.org", 80, "/no-such-page").getStatusCode());
    }
}
