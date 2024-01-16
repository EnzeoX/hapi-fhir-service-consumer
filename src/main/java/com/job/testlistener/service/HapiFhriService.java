package com.job.testlistener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class HapiFhriService {

    private static final Logger log = LoggerFactory.getLogger(HapiFhriService.class);

    private final RestTemplate restTemplate;

    public HapiFhriService(@Qualifier(value = "hapiFhirTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendBundleToHapi(String data) {
        Objects.requireNonNull(data, "Provided data null or empty");
        processAndSend("/Encounter", data);
    }

    private void processAndSend(String endpoint, String data) {
        Objects.requireNonNull(data, "No data provided to send");
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(endpoint, data, String.class);
            log.info(response.getBody());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }
}
