package com.trevisan.CalculadorMIcroServices.Exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ListValuesEmptyException Tests")
class ListValuesEmptyExceptionTest {

    @Test
    @DisplayName("Should create ListValuesEmptyException with default message")
    void testListValuesEmptyException() {
        // Act
        ListValuesEmptyException exception = new ListValuesEmptyException();

        // Assert
        assertNotNull(exception);
        assertEquals("List values is empty!", exception.getMessage());
    }

    @Test
    @DisplayName("Should be instance of RuntimeException")
    void testListValuesEmptyExceptionIsRuntimeException() {
        // Act
        ListValuesEmptyException exception = new ListValuesEmptyException();

        // Assert
        assertTrue(true);
    }

    @Test
    @DisplayName("Should be throwable")
    void testListValuesEmptyExceptionIsThrowable() {
        // Arrange
        ListValuesEmptyException exception = new ListValuesEmptyException();

        // Act & Assert
        assertThrows(ListValuesEmptyException.class, () -> {
            throw exception;
        });
    }

    @Test
    @DisplayName("Should have correct error message")
    void testListValuesEmptyExceptionMessage() {
        // Act
        ListValuesEmptyException exception = new ListValuesEmptyException();

        // Assert
        assertTrue(exception.getMessage().contains("empty"));
    }

}

