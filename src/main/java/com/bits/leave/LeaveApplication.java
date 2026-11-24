
package com.bits.leave;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class LeaveApplication {

    public static void main(String[] args) throws Exception {

        String environment = System.getenv()
                .getOrDefault("APP_ENV", "local");

	int port = Integer.parseInt(
    		System.getenv().getOrDefault("PORT", "8080"));

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
                "Employee Leave Service started on port"+port);

        System.out.println(
                "Environment: " + environment);
    }
}
