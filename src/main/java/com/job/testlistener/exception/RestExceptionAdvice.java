package com.job.testlistener.exception;

import com.job.testlistener.handler.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionAdvice.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.info(e.getMessage());
        return ResponseEntity.status(500).body(e.getMessage());
    }
}