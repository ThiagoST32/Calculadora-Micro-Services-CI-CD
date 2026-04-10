package com.trevisan.CalculadorMIcroServices.Services;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OperationMetricService Tests")
class OperationMetricServiceTest {

    @Mock
    private MeterRegistry meterRegistry;

    @Test
    @DisplayName("Should initialize OperationMetricService with MeterRegistry")
    void testOperationMetricServiceInitialization() {
        // Act
        OperationMetricService service = new OperationMetricService(meterRegistry);

        // Assert
        assertNotNull(service);
        assertNotNull(service.getMeterRegistry());
    }

    @Test
    @DisplayName("Should have MeterRegistry injected")
    void testMeterRegistryInjected() {
        // Act
        OperationMetricService service = new OperationMetricService(meterRegistry);

        // Assert
        assertEquals(meterRegistry, service.getMeterRegistry());
    }

    @Test
    @DisplayName("Should be a service component")
    void testOperationMetricServiceIsNotNull() {
        // Act
        OperationMetricService service = new OperationMetricService(meterRegistry);

        // Assert
        assertNotNull(service);
    }

    @Test
    @DisplayName("Should have metric getters")
    void testMetricGettersExist() {
        // Act
        OperationMetricService service = new OperationMetricService(meterRegistry);

        // Assert
        assertDoesNotThrow(service::getPersistOperationApiSuccessCounter);
        assertDoesNotThrow(service::getPersistOperationApiFailureCounter);
        assertDoesNotThrow(service::getOperationSuccessTimer);
        assertDoesNotThrow(service::getOperationFailureTimer);
    }

}


