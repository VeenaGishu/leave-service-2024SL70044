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
@Test
public void testStartupConfiguration() throws Exception {

    HttpServer server = LeaveApplication.startFromEnvironment(
            java.util.Map.of(
                    "PORT", "0",
                    "APP_ENV", "staging"));

    try {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:"
                        + server.getAddress().getPort()))
                .GET()
                .build();

        HttpResponse<String> response =
                HttpClient.newHttpClient().send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains(
                "Environment: staging"));

    } finally {
        server.stop(0);
    }
}

@Test
public void testDefaultEnvironment() throws Exception {

    HttpServer server = LeaveApplication.startFromEnvironment(
            java.util.Map.of("PORT", "0"));

    try {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:"
                        + server.getAddress().getPort()))
                .GET()
                .build();

        HttpResponse<String> response =
                HttpClient.newHttpClient().send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains(
                "Environment: local"));

    } finally {
        server.stop(0);
    }
}
}
