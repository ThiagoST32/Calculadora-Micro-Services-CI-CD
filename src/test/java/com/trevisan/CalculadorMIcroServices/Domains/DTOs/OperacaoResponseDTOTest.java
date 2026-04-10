package com.trevisan.CalculadorMIcroServices.Domains.DTOs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OperacaoResponseDTO Tests")
class OperacaoResponseDTOTest {

    @Test
    @DisplayName("Should create OperacaoResponseDTO with valid result")
    void testCreateOperacaoResponseDTO() {
        // Arrange & Act
        OperacaoResponseDTO dto = new OperacaoResponseDTO("15.00");

        // Assert
        assertNotNull(dto);
        assertEquals("15.00", dto.result());
    }

    @Test
    @DisplayName("Should create OperacaoResponseDTO with integer result")
    void testCreateOperacaoResponseDTOIntegerResult() {
        // Arrange & Act
        OperacaoResponseDTO dto = new OperacaoResponseDTO("15");

        // Assert
        assertEquals("15", dto.result());
    }

    @Test
    @DisplayName("Should create OperacaoResponseDTO with decimal result")
    void testCreateOperacaoResponseDTODecimalResult() {
        // Arrange & Act
        OperacaoResponseDTO dto = new OperacaoResponseDTO("15.5");

        // Assert
        assertEquals("15.5", dto.result());
    }

    @Test
    @DisplayName("Should create OperacaoResponseDTO with zero result")
    void testCreateOperacaoResponseDTOZeroResult() {
        // Arrange & Act
        OperacaoResponseDTO dto = new OperacaoResponseDTO("0");

        // Assert
        assertEquals("0", dto.result());
    }

    @Test
    @DisplayName("Should test equality of two identical response DTOs")
    void testOperacaoResponseDTOEquality() {
        // Arrange
        OperacaoResponseDTO dto1 = new OperacaoResponseDTO("15.00");
        OperacaoResponseDTO dto2 = new OperacaoResponseDTO("15.00");

        // Assert
        assertEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should test inequality of different response DTOs")
    void testOperacaoResponseDTOInequality() {
        // Arrange
        OperacaoResponseDTO dto1 = new OperacaoResponseDTO("15.00");
        OperacaoResponseDTO dto2 = new OperacaoResponseDTO("20.00");

        // Assert
        assertNotEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should handle large result values")
    void testOperacaoResponseDTOLargeResult() {
        // Arrange & Act
        OperacaoResponseDTO dto = new OperacaoResponseDTO("999999.99");

        // Assert
        assertEquals("999999.99", dto.result());
    }

    @Test
    @DisplayName("Should handle negative result values")
    void testOperacaoResponseDTONegativeResult() {
        // Arrange & Act
        OperacaoResponseDTO dto = new OperacaoResponseDTO("-15.00");

        // Assert
        assertEquals("-15.00", dto.result());
    }

}

