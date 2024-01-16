package com.job.testlistener.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class CommandLineStartup implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(CommandLineStartup.class);

    @Value(value = "${hapi.fhir.server-url}")
    private String serverUrl;

    public void run(String... args) throws IOException {
        if (serverUrl == null) {
            logger.error("SERVER URL IS NULL");
            return;
        }
        logger.info("Testing connection to HAPI FHIR server {}", serverUrl);
        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(
                    serverUrl,
                    String.class
            );
            logger.info("Response from server: {}", result);
        } catch (RuntimeException e) {
            if (e.getMessage().endsWith("Connection refused: connect")) {
                logger.error("No connection to HAPI FHIR Server");
                throw new IOException(e.getMessage());
            } else logger.warn("Connection established but error occurred");
        }
    }

}
