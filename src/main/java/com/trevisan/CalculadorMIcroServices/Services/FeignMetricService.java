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
public class FeignMetricService {

    private Counter operationApiSuccessCounter;
    private Counter operationApiFailureCounter;

    private Timer operationApiSuccessTimer;
    private Timer operationApiFailureTimer;

    private Counter getOperationsApiSuccessCounter;
    private Counter getOperationsApiFailureCounter;

    private Timer getOperationsApiTimerSuccess;
    private Timer getOperationsApiTimerFailure;

    private Counter getPreviousOperationsApiSuccessCounter;
    private Counter getPreviousOperationsApiFailureCounter;

    private Timer getPreviousOperationsApiTimerSuccess;
    private Timer getPreviousOperationsApiTimerFailure;

    private final MeterRegistry meterRegistry;

    @Autowired
    public FeignMetricService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void initMetrics() {

        operationApiSuccessCounter = Counter.builder("com.application.infra.feign.insert")
                .tag("status", "SUCCESS")
                .register(meterRegistry);

        operationApiFailureCounter = Counter.builder("com.application.infra.feign.insert")
                .tag("status", "FAILURE")
                .register(meterRegistry);

        operationApiSuccessTimer = Timer.builder("com.application.infra.feign.insert.response")
                .tag("status", "SUCCESS")
                .register(meterRegistry);

        operationApiFailureTimer = Timer.builder("com.application.infra.feign.insert.response")
                .tag("status", "FAILURE")
                .register(meterRegistry);

        getOperationsApiSuccessCounter = Counter.builder("com.application.infra.feign.getOperations")
                .tag("status", "SUCCESS")
                .register(meterRegistry);

        getOperationsApiFailureCounter = Counter.builder("com.application.infra.feign.getOperations")
                .tag("status", "FAILURE")
                .register(meterRegistry);

        getOperationsApiTimerSuccess = Timer.builder("com.application.infra.feign.getOperations.response")
                .tag("status", "SUCCESS")
                .register(meterRegistry);

        getOperationsApiTimerFailure = Timer.builder("com.application.infra.feign.getOperations.response")
                .tag("status", "FAILURE")
                .register(meterRegistry);

        getPreviousOperationsApiSuccessCounter = Counter.builder("com.application.infra.feign.getPreviousOperations")
                .tag("status", "SUCCESS")
                .register(meterRegistry);

        getPreviousOperationsApiFailureCounter = Counter.builder("com.application.infra.feign.getPreviousOperations")
                .tag("status", "FAILURE")
                .register(meterRegistry);

        getPreviousOperationsApiTimerSuccess = Timer.builder("com.application.infra.feign.getPreviousOperations.response")
                .tag("status", "SUCCESS")
                .register(meterRegistry);

        getPreviousOperationsApiTimerFailure = Timer.builder("com.application.infra.feign.getPreviousOperations.response")
                .tag("status", "FAILURE")
                .register(meterRegistry);
    }
}
