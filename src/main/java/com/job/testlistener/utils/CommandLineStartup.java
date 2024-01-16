package com.job.testlistener.utils;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineStartup implements CommandLineRunner {

    private final HapiFhirUtils hapiFhirUtils;

    public CommandLineStartup(HapiFhirUtils hapiFhirUtils) {
        this.hapiFhirUtils = hapiFhirUtils;
    }

    public void run(String... args) throws IOException {
        hapiFhirUtils.testHapiConnection();
    }

}
