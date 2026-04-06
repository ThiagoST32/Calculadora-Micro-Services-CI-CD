package com.trevisan.CalculadorMIcroServices.infra;

import com.trevisan.CalculadorMIcroServices.Services.FeignMetricService;
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
public class MetricAspectFeign {

    private final FeignMetricService metricService;
    private final Timer.Sample sample = Timer.start();

    @Autowired
    public MetricAspectFeign(FeignMetricService metricService) {
        this.metricService = metricService;
    }

    @Around("execution (* com.trevisan.CalculadorMIcroServices.infra.Feign.OperacaoPersistApi.persistNewOperation(..)")
    public Object persistNewOperationCalled(ProceedingJoinPoint joinPoint) {
        Object method = null;
        var metricSuccess = metricService.getOperationApiSuccessCounter();
        var metricFailure = metricService.getOperationApiFailureCounter();
        var metricTimerSuccess = metricService.getOperationApiSuccessTimer();
        var metricTimerFailure = metricService.getOperationApiFailureTimer();

        try {

            method = joinPoint.proceed();
            metricSuccess.increment();
            sample.stop(metricTimerSuccess);

        } catch (Throwable e) {
            metricFailure.increment();
            sample.stop(metricTimerFailure);
            throw new RuntimeException(e);
        }

        return method;
    }

    @Around("execution (* com.trevisan.CalculadorMIcroServices.infra.Feign.OperacaoPersistApi.getOperations(..)")
    public Object getOperationsCalled(ProceedingJoinPoint joinPoint) {
        Object method = null;
        var metricSuccess = metricService.getOperationApiSuccessCounter();
        var metricFailure = metricService.getOperationApiFailureCounter();
        var metricTimerSuccess = metricService.getOperationApiSuccessTimer();
        var metricTimerFailure = metricService.getOperationApiFailureTimer();

        try {

            method = joinPoint.proceed();
            metricSuccess.increment();
            sample.stop(metricTimerSuccess);

        } catch (Throwable e) {
            metricFailure.increment();
            sample.stop(metricTimerFailure);
            throw new RuntimeException(e);
        }

        return method;
    }

    @Around("execution (* com.trevisan.CalculadorMIcroServices.infra.Feign.OperacaoPersistApi.getPreviousOperation(..)")
    public Object getPreviousOperationsCalled(ProceedingJoinPoint joinPoint) {
        Object method = null;
        var metricSuccess = metricService.getOperationApiSuccessCounter();
        var metricFailure = metricService.getOperationApiFailureCounter();
        var metricTimerSuccess = metricService.getOperationApiSuccessTimer();
        var metricTimerFailure = metricService.getOperationApiFailureTimer();

        try {

            method = joinPoint.proceed();
            metricSuccess.increment();
            sample.stop(metricTimerSuccess);

        } catch (Throwable e) {
            metricFailure.increment();
            sample.stop(metricTimerFailure);
            throw new RuntimeException(e);
        }

        return method;
    }
}
