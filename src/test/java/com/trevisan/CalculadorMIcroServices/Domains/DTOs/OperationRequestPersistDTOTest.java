package com.trevisan.CalculadorMIcroServices.Domains.DTOs;

import com.trevisan.CalculadorMIcroServices.Domains.Enums.TipoDeOperacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OperationRequestPersistDTO Tests")
class OperationRequestPersistDTOTest {

    @Test
    @DisplayName("Should create OperationRequestPersistDTO with valid values")
    void testCreateOperationRequestPersistDTO() {
        // Arrange & Act
        OperationRequestPersistDTO dto = new OperationRequestPersistDTO(
            "10", "5", "15.00", TipoDeOperacao.SOMA
        );

        // Assert
        assertNotNull(dto);
        assertEquals("10", dto.valueOne());
        assertEquals("5", dto.valueTwo());
        assertEquals("15.00", dto.result());
        assertEquals(TipoDeOperacao.SOMA, dto.tipoDeOperacao());
    }

    @Test
    @DisplayName("Should create persist DTO with SUBTRACAO operation")
    void testCreateOperationRequestPersistDTOSubtracao() {
        // Arrange & Act
        OperationRequestPersistDTO dto = new OperationRequestPersistDTO(
            "20", "8", "12.00", TipoDeOperacao.SUBTRACAO
        );

        // Assert
        assertEquals(TipoDeOperacao.SUBTRACAO, dto.tipoDeOperacao());
    }

    @Test
    @DisplayName("Should create persist DTO with MULTIPLICACAO operation")
    void testCreateOperationRequestPersistDTOMultiplicacao() {
        // Arrange & Act
        OperationRequestPersistDTO dto = new OperationRequestPersistDTO(
            "4", "5", "20.00", TipoDeOperacao.MULTIPLICACAO
        );

        // Assert
        assertEquals(TipoDeOperacao.MULTIPLICACAO, dto.tipoDeOperacao());
    }

    @Test
    @DisplayName("Should create persist DTO with DIVISAO operation")
    void testCreateOperationRequestPersistDTODivisao() {
        // Arrange & Act
        OperationRequestPersistDTO dto = new OperationRequestPersistDTO(
            "20", "4", "5.00", TipoDeOperacao.DIVISAO
        );

        // Assert
        assertEquals(TipoDeOperacao.DIVISAO, dto.tipoDeOperacao());
    }

    @Test
    @DisplayName("Should test equality of identical persist DTOs")
    void testOperationRequestPersistDTOEquality() {
        // Arrange
        OperationRequestPersistDTO dto1 = new OperationRequestPersistDTO(
            "10", "5", "15.00", TipoDeOperacao.SOMA
        );
        OperationRequestPersistDTO dto2 = new OperationRequestPersistDTO(
            "10", "5", "15.00", TipoDeOperacao.SOMA
        );

        // Assert
        assertEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should test inequality of different persist DTOs")
    void testOperationRequestPersistDTOInequality() {
        // Arrange
        OperationRequestPersistDTO dto1 = new OperationRequestPersistDTO(
            "10", "5", "15.00", TipoDeOperacao.SOMA
        );
        OperationRequestPersistDTO dto2 = new OperationRequestPersistDTO(
            "10", "5", "15.00", TipoDeOperacao.SUBTRACAO
        );

        // Assert
        assertNotEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should handle decimal values in persist DTO")
    void testOperationRequestPersistDTOWithDecimalValues() {
        // Arrange & Act
        OperationRequestPersistDTO dto = new OperationRequestPersistDTO(
            "10.50", "5.25", "15.75", TipoDeOperacao.SOMA
        );

        // Assert
        assertEquals("10.50", dto.valueOne());
        assertEquals("5.25", dto.valueTwo());
        assertEquals("15.75", dto.result());
    }

    @Test
    @DisplayName("Should handle negative result in persist DTO")
    void testOperationRequestPersistDTONegativeResult() {
        // Arrange & Act
        OperationRequestPersistDTO dto = new OperationRequestPersistDTO(
            "5", "10", "-5.00", TipoDeOperacao.SUBTRACAO
        );

        // Assert
        assertEquals("-5.00", dto.result());
    }

}

