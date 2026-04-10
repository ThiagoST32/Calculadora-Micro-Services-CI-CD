package com.trevisan.CalculadorMIcroServices.Domains.DTOs;

import com.trevisan.CalculadorMIcroServices.Domains.Enums.TipoDeOperacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OperacaoRequestDTO Tests")
class OperacaoRequestDTOTest {

    @Test
    @DisplayName("Should create OperacaoRequestDTO with valid values")
    void testCreateOperacaoRequestDTO() {
        // Arrange & Act
        OperacaoRequestDTO dto = new OperacaoRequestDTO("10", "5", TipoDeOperacao.SOMA);

        // Assert
        assertNotNull(dto);
        assertEquals("10", dto.valueOne());
        assertEquals("5", dto.valueTwo());
        assertEquals(TipoDeOperacao.SOMA, dto.tipoDeOperacao());
    }

    @Test
    @DisplayName("Should create OperacaoRequestDTO with SUBTRACAO operation")
    void testCreateOperacaoRequestDTOSubtracao() {
        // Arrange & Act
        OperacaoRequestDTO dto = new OperacaoRequestDTO("20", "8", TipoDeOperacao.SUBTRACAO);

        // Assert
        assertEquals(TipoDeOperacao.SUBTRACAO, dto.tipoDeOperacao());
    }

    @Test
    @DisplayName("Should create OperacaoRequestDTO with MULTIPLICACAO operation")
    void testCreateOperacaoRequestDTOMultiplicacao() {
        // Arrange & Act
        OperacaoRequestDTO dto = new OperacaoRequestDTO("4", "5", TipoDeOperacao.MULTIPLICACAO);

        // Assert
        assertEquals(TipoDeOperacao.MULTIPLICACAO, dto.tipoDeOperacao());
    }

    @Test
    @DisplayName("Should create OperacaoRequestDTO with DIVISAO operation")
    void testCreateOperacaoRequestDTODivisao() {
        // Arrange & Act
        OperacaoRequestDTO dto = new OperacaoRequestDTO("20", "4", TipoDeOperacao.DIVISAO);

        // Assert
        assertEquals(TipoDeOperacao.DIVISAO, dto.tipoDeOperacao());
    }

    @Test
    @DisplayName("Should test equality of two identical DTOs")
    void testOperacaoRequestDTOEquality() {
        // Arrange
        OperacaoRequestDTO dto1 = new OperacaoRequestDTO("10", "5", TipoDeOperacao.SOMA);
        OperacaoRequestDTO dto2 = new OperacaoRequestDTO("10", "5", TipoDeOperacao.SOMA);

        // Assert
        assertEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should test inequality of different DTOs")
    void testOperacaoRequestDTOInequality() {
        // Arrange
        OperacaoRequestDTO dto1 = new OperacaoRequestDTO("10", "5", TipoDeOperacao.SOMA);
        OperacaoRequestDTO dto2 = new OperacaoRequestDTO("10", "5", TipoDeOperacao.SUBTRACAO);

        // Assert
        assertNotEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should handle decimal values in DTO")
    void testOperacaoRequestDTOWithDecimalValues() {
        // Arrange & Act
        OperacaoRequestDTO dto = new OperacaoRequestDTO("10.50", "5.25", TipoDeOperacao.SOMA);

        // Assert
        assertEquals("10.50", dto.valueOne());
        assertEquals("5.25", dto.valueTwo());
    }

    @Test
    @DisplayName("Should handle comma-separated values in DTO")
    void testOperacaoRequestDTOWithCommaValues() {
        // Arrange & Act
        OperacaoRequestDTO dto = new OperacaoRequestDTO("10,50", "5,25", TipoDeOperacao.SOMA);

        // Assert
        assertEquals("10,50", dto.valueOne());
        assertEquals("5,25", dto.valueTwo());
    }

}

