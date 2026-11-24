package com.bits.leave;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class LeaveApplication {

    public static void main(String[] args) throws Exception {
    startFromEnvironment(System.getenv());
}

public static HttpServer startFromEnvironment(
        java.util.Map<String, String> env) throws Exception {

    String environment = env.getOrDefault("APP_ENV", "local");

    int port = Integer.parseInt(
            env.getOrDefault("PORT", "8080"));

    return startServer(port, environment);
}

    public static HttpServer startServer(int port, String environment)
            throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress("0.0.0.0", port), 0);

        server.createContext("/", exchange -> {

            String response =
                    "Employee Leave Service is running!\n"
                    + "Environment: " + environment + "\n";

            byte[] bytes =
                    response.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                    "Content-Type",
                    "text/plain; charset=UTF-8");

            exchange.sendResponseHeaders(200, bytes.length);

            try (var output = exchange.getResponseBody()) {
                output.write(bytes);
            }
        });

        server.start();

        System.out.println(
                "Employee Leave Service started on port "
                + server.getAddress().getPort());

        System.out.println(
                "Environment: " + environment);

        return server;
    }
}
