package com.job.testlistener.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class HapiFhirUtils {

    private final Logger logger = LoggerFactory.getLogger(HapiFhirUtils.class);

    @Value(value = "${hapi.fhir.server-url}")
    private String serverUrl;

    public void testHapiConnection() throws IOException {
        if (serverUrl == null) {
            logger.error("SERVER URL IS NULL");
            return;
        }
        logger.info("Testing connection to HAPI FHIR server {}", serverUrl);
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result = restTemplate.exchange(
                    serverUrl,
                    HttpMethod.GET,
                    null,
                    String.class
            );
            logger.info("Response from HAPI-FHIR-JPA Server: {}", result.getStatusCode());
        } catch (RuntimeException e) {
            if (e.getMessage().endsWith("Connection refused: connect")) {
                logger.error("No connection to HAPI FHIR Server");
                throw new IOException(e.getMessage());
            } else logger.warn("Connection established but error occurred");
        }
    }
}
