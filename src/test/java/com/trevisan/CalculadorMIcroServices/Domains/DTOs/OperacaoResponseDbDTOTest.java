package com.trevisan.CalculadorMIcroServices.Domains.DTOs;

import com.trevisan.CalculadorMIcroServices.Domains.Enums.TipoDeOperacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OperacaoResponseDbDTO Tests")
class OperacaoResponseDbDTOTest {

    @Test
    @DisplayName("Should create OperacaoResponseDbDTO with valid values")
    void testCreateOperacaoResponseDbDTO() {
        // Arrange & Act
        OperacaoResponseDbDTO dto = new OperacaoResponseDbDTO(
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
    @DisplayName("Should create SUBTRACAO response DB DTO")
    void testCreateOperacaoResponseDbDTOSubtracao() {
        // Arrange & Act
        OperacaoResponseDbDTO dto = new OperacaoResponseDbDTO(
            "20", "8", "12.00", TipoDeOperacao.SUBTRACAO
        );

        // Assert
        assertEquals(TipoDeOperacao.SUBTRACAO, dto.tipoDeOperacao());
        assertEquals("12.00", dto.result());
    }

    @Test
    @DisplayName("Should create MULTIPLICACAO response DB DTO")
    void testCreateOperacaoResponseDbDTOMultiplicacao() {
        // Arrange & Act
        OperacaoResponseDbDTO dto = new OperacaoResponseDbDTO(
            "4", "5", "20.00", TipoDeOperacao.MULTIPLICACAO
        );

        // Assert
        assertEquals(TipoDeOperacao.MULTIPLICACAO, dto.tipoDeOperacao());
        assertEquals("20.00", dto.result());
    }

    @Test
    @DisplayName("Should create DIVISAO response DB DTO")
    void testCreateOperacaoResponseDbDTODivisao() {
        // Arrange & Act
        OperacaoResponseDbDTO dto = new OperacaoResponseDbDTO(
            "20", "4", "5.00", TipoDeOperacao.DIVISAO
        );

        // Assert
        assertEquals(TipoDeOperacao.DIVISAO, dto.tipoDeOperacao());
        assertEquals("5.00", dto.result());
    }

    @Test
    @DisplayName("Should test equality of identical DB DTOs")
    void testOperacaoResponseDbDTOEquality() {
        // Arrange
        OperacaoResponseDbDTO dto1 = new OperacaoResponseDbDTO(
            "10", "5", "15.00", TipoDeOperacao.SOMA
        );
        OperacaoResponseDbDTO dto2 = new OperacaoResponseDbDTO(
            "10", "5", "15.00", TipoDeOperacao.SOMA
        );

        // Assert
        assertEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should test inequality of different DB DTOs")
    void testOperacaoResponseDbDTOInequality() {
        // Arrange
        OperacaoResponseDbDTO dto1 = new OperacaoResponseDbDTO(
            "10", "5", "15.00", TipoDeOperacao.SOMA
        );
        OperacaoResponseDbDTO dto2 = new OperacaoResponseDbDTO(
            "10", "5", "15.00", TipoDeOperacao.SUBTRACAO
        );

        // Assert
        assertNotEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should handle decimal values in DB DTO")
    void testOperacaoResponseDbDTOWithDecimalValues() {
        // Arrange & Act
        OperacaoResponseDbDTO dto = new OperacaoResponseDbDTO(
            "10.50", "5.25", "15.75", TipoDeOperacao.SOMA
        );

        // Assert
        assertEquals("10.50", dto.valueOne());
        assertEquals("5.25", dto.valueTwo());
        assertEquals("15.75", dto.result());
    }

    @Test
    @DisplayName("Should handle all four operations in DB DTO")
    void testOperacaoResponseDbDTOAllOperations() {
        // Test SOMA
        OperacaoResponseDbDTO soma = new OperacaoResponseDbDTO(
            "10", "5", "15.00", TipoDeOperacao.SOMA
        );
        assertEquals(TipoDeOperacao.SOMA, soma.tipoDeOperacao());

        // Test SUBTRACAO
        OperacaoResponseDbDTO subtracao = new OperacaoResponseDbDTO(
            "10", "5", "5.00", TipoDeOperacao.SUBTRACAO
        );
        assertEquals(TipoDeOperacao.SUBTRACAO, subtracao.tipoDeOperacao());

        // Test MULTIPLICACAO
        OperacaoResponseDbDTO multiplicacao = new OperacaoResponseDbDTO(
            "10", "5", "50.00", TipoDeOperacao.MULTIPLICACAO
        );
        assertEquals(TipoDeOperacao.MULTIPLICACAO, multiplicacao.tipoDeOperacao());

        // Test DIVISAO
        OperacaoResponseDbDTO divisao = new OperacaoResponseDbDTO(
            "10", "5", "2.00", TipoDeOperacao.DIVISAO
        );
        assertEquals(TipoDeOperacao.DIVISAO, divisao.tipoDeOperacao());
    }

}

