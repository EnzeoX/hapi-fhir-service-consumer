package com.job.testlistener.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;

@EnableScheduling
@Configuration
public class AppConfig {

    @Value(value = "${hapi.fhir.server-url}")
    private String mainEndpoint;

    @Bean(name = "hapiFhirTemplate")
    public RestTemplate hapiFhirTemplate(RestTemplateBuilder templateBuilder) {
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory(mainEndpoint);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
        restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
        restTemplate.getInterceptors().add((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.valueOf("application/fhir+json"));
            return execution.execute(request, body);
        });
        return restTemplate;
    }


}
