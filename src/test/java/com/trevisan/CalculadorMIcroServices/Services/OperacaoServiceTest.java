package com.trevisan.CalculadorMIcroServices.Services;

import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperacaoRequestDTO;
import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperacaoResponseDTO;
import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperationRequestPersistDTO;
import com.trevisan.CalculadorMIcroServices.Domains.Enums.TipoDeOperacao;
import com.trevisan.CalculadorMIcroServices.Utils.OperacoesUtils;
import com.trevisan.CalculadorMIcroServices.infra.Feign.OperacaoPersistApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OperacaoService Tests")
class OperacaoServiceTest {

    private OperacaoService service;

    @Mock
    private OperacoesUtils utils;

    @Mock
    private OperacaoPersistApi persistApi;

    @BeforeEach
    void setUp() {
        service = new OperacaoService(utils, persistApi);
    }

    // ========== Tests for calculoOperacao ==========

    @Test
    @DisplayName("Should perform SOMA operation with valid request")
    void testCalculoOperacaoSOMA() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("10.00", "5.00", TipoDeOperacao.SOMA);

        when(utils.verifyIfValueContainsComma(any())).thenReturn(false);
        when(utils.verifyIfValueContaisDecimal(any())).thenReturn(true);
        when(utils.calculoComNumerosDecimais(any(), eq(TipoDeOperacao.SOMA)))
            .thenReturn(new BigDecimal("15.00"));

        // Act
        OperacaoResponseDTO response = service.calculoOperacao(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("15.00", response.result());

        // Verify that persistence was called
        ArgumentCaptor<OperationRequestPersistDTO> captor = ArgumentCaptor.forClass(OperationRequestPersistDTO.class);
        verify(persistApi, times(1)).persistNewOperation(captor.capture());

        OperationRequestPersistDTO persistedDTO = captor.getValue();
        assertEquals("10.00", persistedDTO.valueOne());
        assertEquals("5.00", persistedDTO.valueTwo());
        assertEquals(TipoDeOperacao.SOMA, persistedDTO.tipoDeOperacao());
    }

    @Test
    @DisplayName("Should perform SUBTRACAO operation with valid request")
    void testCalculoOperacaoSubtracao() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("10.00", "3.00", TipoDeOperacao.SUBTRACAO);

        when(utils.verifyIfValueContainsComma(any())).thenReturn(false);
        when(utils.verifyIfValueContaisDecimal(any())).thenReturn(true);
        when(utils.calculoComNumerosDecimais(any(), eq(TipoDeOperacao.SUBTRACAO)))
            .thenReturn(new BigDecimal("7.00"));

        // Act
        OperacaoResponseDTO response = service.calculoOperacao(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("7.00", response.result());
    }

    @Test
    @DisplayName("Should perform MULTIPLICACAO operation with valid request")
    void testCalculoOperacaoMultiplicacao() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("4.00", "5.00", TipoDeOperacao.MULTIPLICACAO);

        when(utils.verifyIfValueContainsComma(any())).thenReturn(false);
        when(utils.verifyIfValueContaisDecimal(any())).thenReturn(true);
        when(utils.calculoComNumerosDecimais(any(), eq(TipoDeOperacao.MULTIPLICACAO)))
            .thenReturn(new BigDecimal("20.00"));

        // Act
        OperacaoResponseDTO response = service.calculoOperacao(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("20.00", response.result());
    }

    @Test
    @DisplayName("Should perform DIVISAO operation with valid request")
    void testCalculoOperacaoDivisao() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("20.00", "4.00", TipoDeOperacao.DIVISAO);

        when(utils.verifyIfValueContainsComma(any())).thenReturn(false);
        when(utils.verifyIfValueContaisDecimal(any())).thenReturn(true);
        when(utils.calculoComNumerosDecimais(any(), eq(TipoDeOperacao.DIVISAO)))
            .thenReturn(new BigDecimal("5.00"));

        // Act
        OperacaoResponseDTO response = service.calculoOperacao(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("5.00", response.result());
    }

    @Test
    @DisplayName("Should handle calculation with comma-separated values")
    void testCalculoOperacaoWithCommaValues() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("10,5", "5,3", TipoDeOperacao.SOMA);

        when(utils.verifyIfValueContainsComma(any())).thenReturn(true);
        when(utils.mapValuesToFloat(any(), eq(TipoDeOperacao.SOMA)))
            .thenReturn(15.8f);

        // Act
        OperacaoResponseDTO response = service.calculoOperacao(requestDTO);

        // Assert
        assertNotNull(response);
        assertNotNull(response.result());

        verify(utils).verifyIfValueContainsComma(any());
        verify(utils).mapValuesToFloat(any(), eq(TipoDeOperacao.SOMA));
        verify(persistApi).persistNewOperation(any());
    }

    @Test
    @DisplayName("Should call verification methods before calculation")
    void testCalculoOperacaoCallsVerification() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("10", "5", TipoDeOperacao.SOMA);

        when(utils.verifyIfValueContainsComma(any())).thenReturn(false);
        when(utils.verifyIfValueContaisDecimal(any())).thenReturn(true);
        when(utils.calculoComNumerosDecimais(any(), eq(TipoDeOperacao.SOMA)))
            .thenReturn(new BigDecimal("15"));

        // Act
        service.calculoOperacao(requestDTO);

        // Assert
        verify(utils).verifyIfValueContainsComma(any());
        verify(utils).verifyIfValueContaisDecimal(any());
    }

    @Test
    @DisplayName("Should persist operation after calculation")
    void testCalculoOperacaoPersistsOperation() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("10", "5", TipoDeOperacao.SOMA);

        when(utils.verifyIfValueContainsComma(any())).thenReturn(false);
        when(utils.verifyIfValueContaisDecimal(any())).thenReturn(true);
        when(utils.calculoComNumerosDecimais(any(), any()))
            .thenReturn(new BigDecimal("15"));

        // Act
        service.calculoOperacao(requestDTO);

        // Assert
        verify(persistApi, times(1)).persistNewOperation(any(OperationRequestPersistDTO.class));
    }

    @Test
    @DisplayName("Should return OperacaoResponseDTO with result")
    void testCalculoOperacaoReturnsResponse() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("100", "20", TipoDeOperacao.DIVISAO);

        when(utils.verifyIfValueContainsComma(any())).thenReturn(false);
        when(utils.verifyIfValueContaisDecimal(any())).thenReturn(true);
        when(utils.calculoComNumerosDecimais(any(), eq(TipoDeOperacao.DIVISAO)))
            .thenReturn(new BigDecimal("5.00"));

        // Act
        OperacaoResponseDTO response = service.calculoOperacao(requestDTO);

        // Assert
        assertNotNull(response);
        assertNotNull(response.result());
        assertFalse(response.result().isEmpty());
    }

}


