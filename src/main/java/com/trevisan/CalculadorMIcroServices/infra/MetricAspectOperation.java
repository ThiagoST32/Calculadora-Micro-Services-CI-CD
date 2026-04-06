package com.trevisan.CalculadorMIcroServices.infra;

import com.trevisan.CalculadorMIcroServices.Services.OperationMetricService;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MetricAspectOperation {

    private final Timer.Sample sample = Timer.start();
    private final OperationMetricService metricService;

    @Autowired
    public MetricAspectOperation(OperationMetricService metricService) {
        this.metricService = metricService;
    }

    @Around("execution(* com.trevisa.CalcladorMIcroServices.Services.OperacaoService.calculoOperacao(..)")
    public Object trackOperationCalc(ProceedingJoinPoint joinPoint) {
        Object method = null;
        var metricSuccess = metricService.getPersistOperationApiSuccessCounter();
        var metricFailed = metricService.getPersistOperationApiFailureCounter();
        var metricTimerSuccess = metricService.getOperationSuccessTimer();
        var metricTimerFailure = metricService.getOperationFailureTimer();

        try {

            method = joinPoint.proceed();
            metricSuccess.increment();
            sample.stop(metricTimerSuccess);

        } catch (Throwable e) {
            metricFailed.increment();
            sample.stop(metricTimerFailure);
            throw new RuntimeException(e);
        }

        return method;
    }
}
