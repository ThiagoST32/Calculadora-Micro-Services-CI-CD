package com.trevisan.CalculadorMIcroServices.Services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class OperationMetricService {

    private Counter persistOperationApiSuccessCounter;
    private Counter persistOperationApiFailureCounter;

    private Timer operationSuccessTimer;
    private Timer operationFailureTimer;

    private final MeterRegistry meterRegistry;

    @Autowired
    public OperationMetricService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void initMetrics() {
        persistOperationApiSuccessCounter = Counter.builder("com.application.operation.service.calc")
                .tag("status", "SUCCESS")
                .register(meterRegistry);

        persistOperationApiFailureCounter = Counter.builder("com.application.operation.service.calc")
                .tag("status", "FAILURE")
                .register(meterRegistry);

        operationSuccessTimer = Timer.builder("com.application.operation.service.calc.response")
                .tag("status", "SUCCESS")
                .register(meterRegistry);

        operationFailureTimer = Timer.builder("com.application.operation.service.calc.response")
                .tag("status", "FAILURE")
                .register(meterRegistry);
    }
}
