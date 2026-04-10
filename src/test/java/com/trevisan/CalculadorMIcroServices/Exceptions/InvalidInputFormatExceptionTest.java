package com.trevisan.CalculadorMIcroServices.Exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InvalidInputFormatException Tests")
class InvalidInputFormatExceptionTest {

    @Test
    @DisplayName("Should create InvalidInputFormatException with default message")
    void testInvalidInputFormatException() {
        // Act
        InvalidInputFormatException exception = new InvalidInputFormatException();

        // Assert
        assertNotNull(exception);
        assertEquals("Invalid format value!", exception.getMessage());
    }

    @Test
    @DisplayName("Should be instance of RuntimeException")
    void testInvalidInputFormatExceptionIsRuntimeException() {
        // Act
        InvalidInputFormatException exception = new InvalidInputFormatException();

        // Assert
        assertTrue(true);
    }

    @Test
    @DisplayName("Should be throwable")
    void testInvalidInputFormatExceptionIsThrowable() {
        // Arrange
        InvalidInputFormatException exception = new InvalidInputFormatException();

        // Act & Assert
        assertThrows(InvalidInputFormatException.class, () -> {
            throw exception;
        });
    }

    @Test
    @DisplayName("Should have correct error message")
    void testInvalidInputFormatExceptionMessage() {
        // Act
        InvalidInputFormatException exception = new InvalidInputFormatException();

        // Assert
        assertTrue(exception.getMessage().contains("Invalid"));
        assertTrue(exception.getMessage().contains("format"));
    }

}

