package com.openpay.marvel.api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnableMockServer {

    protected ClientAndServer mockServer;

    @BeforeAll
    public void startServer() {
        mockServer = startClientAndServer(8080);
    }

    @AfterAll
    public void stopServer() {
        mockServer.stop();
    }

    protected String getResourceContent(String location) {

        File file = new File("src/test/resources/" + location);

        try (InputStream is = new FileInputStream(file)) {
            return new String(Objects.requireNonNull(is).readAllBytes());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected MockServerClient createExpectation() {
        return new MockServerClient("localhost", 8080);
    }
}
