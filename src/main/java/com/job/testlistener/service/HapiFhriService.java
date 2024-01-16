package com.job.testlistener.service;

import com.job.testlistener.utils.HapiFhirUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class HapiFhriService {

    private static final Logger log = LoggerFactory.getLogger(HapiFhriService.class);

    @Value(value = "${application.max-queue-size}")
    private int MAX_QUEUE_SIZE;

    private final RestTemplate restTemplate;

    private final HapiFhirUtils hapiFhirUtils;

    private final ConcurrentLinkedQueue<String> oldMessages = new ConcurrentLinkedQueue<>();

    public HapiFhriService(@Qualifier(value = "hapiFhirTemplate") RestTemplate restTemplate,
                           HapiFhirUtils hapiFhirUtils) {
        this.restTemplate = restTemplate;
        this.hapiFhirUtils = hapiFhirUtils;
    }


    public void sendEncounterBundleToHapi(String data) {
        Objects.requireNonNull(data, "Provided data null or empty");
        try {
            processAndSend("/Encounter", data);
        } catch (RuntimeException e) {
            if (this.oldMessages.size() <= MAX_QUEUE_SIZE) {
                this.oldMessages.add(data);
            } else {
                log.warn("Messages queue limit is reached!");
            }
        }
    }

    private void processAndSend(String endpoint, String data) throws RuntimeException {
        Objects.requireNonNull(data, "No data provided to send");
        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, data, String.class);
        log.info(response.getBody());
    }

    // cache unsended messages to kafka and send them later
    // by now is for Encounter Bundles only
    @Scheduled(fixedDelay = 10000L)
    public void checkForMessageToResend() {
        if (this.oldMessages.size() == 0) {
            return;
        }
        try {
            hapiFhirUtils.testHapiConnection();
        }catch (IOException e) {
            log.error(e.getMessage());
            return;
        }
        log.info("List of old messages in not empty");
        while (!this.oldMessages.isEmpty()){
            sendEncounterBundleToHapi(this.oldMessages.poll());
        }
    }
}
