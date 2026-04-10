package com.trevisan.CalculadorMIcroServices.Services;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FeignMetricService Tests")
class FeignMetricServiceTest {

    @Mock
    private MeterRegistry meterRegistry;

    @Test
    @DisplayName("Should initialize FeignMetricService with MeterRegistry")
    void testFeignMetricServiceInitialization() {
        // Act
        FeignMetricService service = new FeignMetricService(meterRegistry);

        // Assert
        assertNotNull(service);
        assertNotNull(service.getMeterRegistry());
    }

    @Test
    @DisplayName("Should have MeterRegistry injected")
    void testMeterRegistryInjected() {
        // Act
        FeignMetricService service = new FeignMetricService(meterRegistry);

        // Assert
        assertEquals(meterRegistry, service.getMeterRegistry());
    }

    @Test
    @DisplayName("Should be a service component")
    void testFeignMetricServiceIsNotNull() {
        // Act
        FeignMetricService service = new FeignMetricService(meterRegistry);

        // Assert
        assertNotNull(service);
    }

    @Test
    @DisplayName("Should have all metric fields as getters")
    void testGettersExist() {
        // Act
        FeignMetricService service = new FeignMetricService(meterRegistry);

        // Assert
        assertDoesNotThrow(service::getOperationApiSuccessCounter);
        assertDoesNotThrow(service::getOperationApiFailureCounter);
    }

}


