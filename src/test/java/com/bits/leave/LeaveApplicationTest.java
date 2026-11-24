package com.bits.leave;

import com.sun.net.httpserver.HttpServer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class LeaveApplicationTest {

    @Test
    public void testLocalEnvironment() throws Exception {
        verifyEnvironment("local");
    }

    @Test
    public void testStagingEnvironment() throws Exception {
        verifyEnvironment("staging");
    }

    @Test
    public void testProductionEnvironment() throws Exception {
        verifyEnvironment("production");
    }

    private void verifyEnvironment(String environment)
            throws Exception {

        HttpServer server =
                LeaveApplication.startServer(0, environment);

        try {
            int port = server.getAddress().getPort();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient().send(
                            request,
                            HttpResponse.BodyHandlers.ofString());

            assertEquals(200, response.statusCode());

            assertTrue(response.body().contains(
                    "Employee Leave Service is running!"));

            assertTrue(response.body().contains(
                    "Environment: " + environment));

        } finally {
            server.stop(0);
        }
    }
}
