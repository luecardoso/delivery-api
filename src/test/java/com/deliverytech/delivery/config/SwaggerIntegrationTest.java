package com.deliverytech.delivery.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwaggerIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testSwaggerUIAccessible() {
        String url = "http://localhost:" + port + "/swagger-ui.html";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().contains("swagger"));
    }

    @Test
    public void testApiDocsAccessible() {
        String url = "http://localhost:" + port + "/api-docs";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().contains("openapi"));
        Assertions.assertTrue(response.getBody().contains("DeliveryTech API"));
    }

    @Test
    public void testApiDocsContainsExpectedEndpoints() {
        String url = "http://localhost:" + port + "/api-docs";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String body = response.getBody();
        Assertions.assertTrue(body.contains("/api/restaurantes"));
        Assertions.assertTrue(body.contains("/api/produtos"));
        Assertions.assertTrue(body.contains("/api/pedidos"));
        Assertions.assertTrue(body.contains("/api/auth"));
    }
}
