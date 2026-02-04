package com.trevisan.CalculadorMIcroServices.infra.Actuator;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.health.autoconfigure.contributor.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConditionalOnEnabledHealthIndicator("external_service_health")
public class ExternalServiceHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate;

    @Autowired
    public ExternalServiceHealthIndicator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public @Nullable Health health() {
        String apiUrl = "http://localhost/api/v1/calc";
        int statusCode = restTemplate.getForEntity(apiUrl, String.class).getStatusCode().value();
        return (switch (statusCode){
            case 200, 204 -> Health
                    .up()
                    .withDetail("Operation_Api", "Service is Up and Running")
                    .withDetail("url", "http://localhost:8080/api/v1/calc")
                    .build();

            case 402, 404 -> Health
                    .down()
                    .withDetail("Operation_Api", "Service is Down")
                    .withDetail("url", "http://localhost:8080/api/v1/calc")
                    .build();

            default -> Health
                    .unknown()
                    .withException(new RuntimeException("It is not possible to collect the health data from the application"))
                    .build();
        });
    }
}
