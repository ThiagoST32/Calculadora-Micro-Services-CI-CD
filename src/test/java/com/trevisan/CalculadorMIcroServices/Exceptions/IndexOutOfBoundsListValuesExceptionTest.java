package com.trevisan.CalculadorMIcroServices.Exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IndexOutOfBoundsListValuesException Tests")
class IndexOutOfBoundsListValuesExceptionTest {

    @Test
    @DisplayName("Should create IndexOutOfBoundsListValuesException with default message")
    void testIndexOutOfBoundsListValuesException() {
        // Act
        IndexOutOfBoundsListValuesException exception = new IndexOutOfBoundsListValuesException();

        // Assert
        assertNotNull(exception);
        assertEquals("Is necessary 2 values to operating", exception.getMessage());
    }

    @Test
    @DisplayName("Should be instance of RuntimeException")
    void testIndexOutOfBoundsListValuesExceptionIsRuntimeException() {
        // Act
        IndexOutOfBoundsListValuesException exception = new IndexOutOfBoundsListValuesException();

        // Assert
        assertTrue(true);
    }

    @Test
    @DisplayName("Should be throwable")
    void testIndexOutOfBoundsListValuesExceptionIsThrowable() {
        // Arrange
        IndexOutOfBoundsListValuesException exception = new IndexOutOfBoundsListValuesException();

        // Act & Assert
        assertThrows(IndexOutOfBoundsListValuesException.class, () -> {
            throw exception;
        });
    }

    @Test
    @DisplayName("Should have correct error message")
    void testIndexOutOfBoundsListValuesExceptionMessage() {
        // Act
        IndexOutOfBoundsListValuesException exception = new IndexOutOfBoundsListValuesException();

        // Assert
        assertTrue(exception.getMessage().contains("2 values"));
    }

}

