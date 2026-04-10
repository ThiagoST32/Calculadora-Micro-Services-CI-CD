package com.trevisan.CalculadorMIcroServices.Controllers;

import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperacaoRequestDTO;
import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperacaoResponseDTO;
import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperacaoResponseDbDTO;
import com.trevisan.CalculadorMIcroServices.Domains.Enums.TipoDeOperacao;
import com.trevisan.CalculadorMIcroServices.Services.OperacaoService;
import com.trevisan.CalculadorMIcroServices.infra.Feign.OperacaoPersistApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OperacaoController Tests")
class OperacaoControllerTest {

    private OperacaoController controller;

    @Mock
    private OperacaoService service;

    @Mock
    private OperacaoPersistApi persistApi;

    @BeforeEach
    void setUp() {
        controller = new OperacaoController(service, persistApi);
    }

    @Test
    @DisplayName("Should call service and return SOMA result with 200 OK")
    void testCallOperatingServiceSoma() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("10", "5", TipoDeOperacao.SOMA);
        OperacaoResponseDTO expectedResponse = new OperacaoResponseDTO("15");

        when(service.calculoOperacao(requestDTO)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<OperacaoResponseDTO> response = controller.callOperatingService(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("15", response.getBody().result());
        verify(service, times(1)).calculoOperacao(requestDTO);
    }

    @Test
    @DisplayName("Should call service and return SUBTRACAO result")
    void testCallOperatingServiceSubtracao() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("10", "3", TipoDeOperacao.SUBTRACAO);
        OperacaoResponseDTO expectedResponse = new OperacaoResponseDTO("7");

        when(service.calculoOperacao(requestDTO)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<OperacaoResponseDTO> response = controller.callOperatingService(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert response.getBody() != null;
        assertEquals("7", response.getBody().result());
    }

    @Test
    @DisplayName("Should call service and return MULTIPLICACAO result")
    void testCallOperatingServiceMultiplicacao() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("4", "5", TipoDeOperacao.MULTIPLICACAO);
        OperacaoResponseDTO expectedResponse = new OperacaoResponseDTO("20");

        when(service.calculoOperacao(requestDTO)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<OperacaoResponseDTO> response = controller.callOperatingService(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert response.getBody() != null;
        assertEquals("20", response.getBody().result());
    }

    @Test
    @DisplayName("Should call service and return DIVISAO result")
    void testCallOperatingServiceDivisao() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("20", "4", TipoDeOperacao.DIVISAO);
        OperacaoResponseDTO expectedResponse = new OperacaoResponseDTO("5");

        when(service.calculoOperacao(requestDTO)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<OperacaoResponseDTO> response = controller.callOperatingService(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert response.getBody() != null;
        assertEquals("5", response.getBody().result());
    }

    @Test
    @DisplayName("Should return response entity with OK status")
    void testCallOperatingServiceReturnsOkStatus() {
        // Arrange
        OperacaoRequestDTO requestDTO = new OperacaoRequestDTO("10", "5", TipoDeOperacao.SOMA);
        when(service.calculoOperacao(any())).thenReturn(new OperacaoResponseDTO("15"));

        // Act
        ResponseEntity<OperacaoResponseDTO> response = controller.callOperatingService(requestDTO);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // ========== Tests for getAllOperations ==========

    @Test
    @DisplayName("Should retrieve all operations from persistence API")
    void testGetAllOperations() {
        // Arrange
        List<OperacaoResponseDbDTO> expectedOperations = List.of(
            new OperacaoResponseDbDTO("10", "5", "15", TipoDeOperacao.SOMA),
            new OperacaoResponseDbDTO("20", "4", "5", TipoDeOperacao.DIVISAO)
        );

        when(persistApi.getOperations()).thenReturn(expectedOperations);

        // Act
        ResponseEntity<List<OperacaoResponseDbDTO>> response = controller.getAllOperations();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(persistApi, times(1)).getOperations();
    }

    @Test
    @DisplayName("Should return empty list when no operations exist")
    void testGetAllOperationsEmpty() {
        // Arrange
        when(persistApi.getOperations()).thenReturn(List.of());

        // Act
        ResponseEntity<List<OperacaoResponseDbDTO>> response = controller.getAllOperations();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    @DisplayName("Should return operations with correct data")
    void testGetAllOperationsWithCorrectData() {
        // Arrange
        OperacaoResponseDbDTO operation = new OperacaoResponseDbDTO("100", "20", "5.00", TipoDeOperacao.DIVISAO);
        List<OperacaoResponseDbDTO> expectedOperations = List.of(operation);

        when(persistApi.getOperations()).thenReturn(expectedOperations);

        // Act
        ResponseEntity<List<OperacaoResponseDbDTO>> response = controller.getAllOperations();

        // Assert
        assertNotNull(response.getBody());
        OperacaoResponseDbDTO retrievedOperation = response.getBody().getFirst();
        assertEquals("100", retrievedOperation.valueOne());
        assertEquals("20", retrievedOperation.valueTwo());
        assertEquals("5.00", retrievedOperation.result());
        assertEquals(TipoDeOperacao.DIVISAO, retrievedOperation.tipoDeOperacao());
    }

    // ========== Tests for getPreviousOperation ==========

    @Test
    @DisplayName("Should retrieve the last operation from persistence API")
    void testGetPreviousOperation() {
        // Arrange
        OperacaoResponseDbDTO lastOperation = new OperacaoResponseDbDTO(
            "50", "10", "5", TipoDeOperacao.DIVISAO
        );

        when(persistApi.getPreviousOperation()).thenReturn(lastOperation);

        // Act
        ResponseEntity<OperacaoResponseDbDTO> response = controller.getPreviousOperation();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("50", response.getBody().valueOne());
        assertEquals("10", response.getBody().valueTwo());
        verify(persistApi, times(1)).getPreviousOperation();
    }

    @Test
    @DisplayName("Should return previous operation with correct values")
    void testGetPreviousOperationWithCorrectValues() {
        // Arrange
        OperacaoResponseDbDTO lastOperation = new OperacaoResponseDbDTO(
            "15", "3", "45.00", TipoDeOperacao.MULTIPLICACAO
        );

        when(persistApi.getPreviousOperation()).thenReturn(lastOperation);

        // Act
        ResponseEntity<OperacaoResponseDbDTO> response = controller.getPreviousOperation();

        // Assert
        OperacaoResponseDbDTO operation = response.getBody();
        assert operation != null;
        assertEquals("15", operation.valueOne());
        assertEquals("3", operation.valueTwo());
        assertEquals("45.00", operation.result());
        assertEquals(TipoDeOperacao.MULTIPLICACAO, operation.tipoDeOperacao());
    }

    @Test
    @DisplayName("Should return 200 OK status for previous operation")
    void testGetPreviousOperationStatus() {
        // Arrange
        when(persistApi.getPreviousOperation()).thenReturn(
            new OperacaoResponseDbDTO("10", "5", "15", TipoDeOperacao.SOMA)
        );

        // Act
        ResponseEntity<OperacaoResponseDbDTO> response = controller.getPreviousOperation();

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}

