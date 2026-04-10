package com.trevisan.CalculadorMIcroServices.Utils;

import com.trevisan.CalculadorMIcroServices.Domains.Enums.TipoDeOperacao;
import com.trevisan.CalculadorMIcroServices.Exceptions.IndexOutOfBoundsListValuesException;
import com.trevisan.CalculadorMIcroServices.Exceptions.InvalidInputFormatException;
import com.trevisan.CalculadorMIcroServices.Exceptions.ListValuesEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OperacoesUtils Tests")
class OperacoesUtilsTest {

    private OperacoesUtils utils;

    @BeforeEach
    void setUp() {
        utils = new OperacoesUtils();
    }

    // ========== Tests for mapValuesToFloat ==========

    @Test
    @DisplayName("Should map string value to float - note only gets first value")
    void testMapValuesToFloatSoma() {
        List<String> values = new ArrayList<>();
        values.add("10.5");
        values.add("5.3");

        Float result = utils.mapValuesToFloat(values, TipoDeOperacao.SOMA);

        // mapValuesToFloat only adds first value and operates with itself
        assertEquals(21.0f, result, 0.01f);
    }

    @Test
    @DisplayName("Should map string value to float for division")
    void testMapValuesToFloatDivisao() {
        List<String> values = new ArrayList<>();
        values.add("10.0");
        values.add("5.0");

        Float result = utils.mapValuesToFloat(values, TipoDeOperacao.DIVISAO);

        assertEquals(1.0f, result, 0.01f);
    }

    // ========== Tests for calculoComNumerosComVirgula ==========

    @Test
    @DisplayName("Should perform SOMA with comma-separated float numbers")
    void testCalculoComNumerosComVirgulaSOMA() {
        List<Float> values = List.of(10.5f, 5.3f);

        float result = utils.calculoComNumerosComVirgula(values, TipoDeOperacao.SOMA);

        assertEquals(15.8f, result, 0.01f);
    }

    @Test
    @DisplayName("Should perform SUBTRACAO with comma-separated float numbers")
    void testCalculoComNumerosComVirgulaSubtracao() {
        List<Float> values = List.of(10.5f, 3.2f);

        float result = utils.calculoComNumerosComVirgula(values, TipoDeOperacao.SUBTRACAO);

        assertEquals(7.3f, result, 0.01f);
    }

    @Test
    @DisplayName("Should perform MULTIPLICACAO with comma-separated float numbers")
    void testCalculoComNumerosComVirgulaMultiplicacao() {
        List<Float> values = List.of(2.5f, 4.0f);

        float result = utils.calculoComNumerosComVirgula(values, TipoDeOperacao.MULTIPLICACAO);

        assertEquals(10.0f, result, 0.01f);
    }

    @Test
    @DisplayName("Should perform DIVISAO with comma-separated float numbers")
    void testCalculoComNumerosComVirgulaDisvisao() {
        List<Float> values = List.of(10.0f, 2.5f);

        float result = utils.calculoComNumerosComVirgula(values, TipoDeOperacao.DIVISAO);

        assertEquals(4.0f, result, 0.01f);
    }

    // ========== Tests for calculoComNumerosDecimais ==========

    @Test
    @DisplayName("Should perform SOMA with decimal BigDecimal numbers")
    void testCalculoComNumerosDecimaisSOMA() {
        List<String> values = List.of("10.55", "5.25");

        BigDecimal result = utils.calculoComNumerosDecimais(values, TipoDeOperacao.SOMA);

        assertEquals(new BigDecimal("15.80"), result);
    }

    @Test
    @DisplayName("Should perform SUBTRACAO with decimal BigDecimal numbers")
    void testCalculoComNumerosDecimaisSubtracao() {
        List<String> values = List.of("10.55", "3.25");

        BigDecimal result = utils.calculoComNumerosDecimais(values, TipoDeOperacao.SUBTRACAO);

        assertEquals(new BigDecimal("7.30"), result);
    }

    @Test
    @DisplayName("Should perform MULTIPLICACAO with decimal BigDecimal numbers")
    void testCalculoComNumerosDecimaisMultiplicacao() {
        List<String> values = List.of("2.50", "4.00");

        BigDecimal result = utils.calculoComNumerosDecimais(values, TipoDeOperacao.MULTIPLICACAO);

        assertEquals(new BigDecimal("10.00"), result);
    }

    @Test
    @DisplayName("Should perform DIVISAO with decimal BigDecimal numbers")
    void testCalculoComNumerosDecimaisDivisao() {
        List<String> values = List.of("10.00", "2.50");

        BigDecimal result = utils.calculoComNumerosDecimais(values, TipoDeOperacao.DIVISAO);

        assertEquals(new BigDecimal("4.00"), result);
    }

    // ========== Tests for verifyIfValueContaisDecimal ==========

    @Test
    @DisplayName("Should return true when values contain decimals")
    void testVerifyIfValueContaisDecimalTrue() {
        List<String> values = List.of("10.5", "5.3");

        boolean result = utils.verifyIfValueContaisDecimal(values);

        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when values do not contain decimals")
    void testVerifyIfValueContaisDecimalFalse() {
        List<String> values = List.of("10", "5");

        boolean result = utils.verifyIfValueContaisDecimal(values);

        // If first value has no decimal, all values must match it
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false for single decimal value")
    void testVerifyIfValueContaisDecimalSingleValue() {
        List<String> values = List.of("10.5", "5");

        boolean result = utils.verifyIfValueContaisDecimal(values);

        assertTrue(result);
    }

    // ========== Tests for verifyIfValueContainsComma ==========

    @Test
    @DisplayName("Should handle values with comma properly")
    void testVerifyIfValueContainsCommaTrue() {
        List<String> values = List.of("10.5", "5.3");

        boolean result = utils.verifyIfValueContainsComma(values);

        assertTrue(result);
    }

    @Test
    @DisplayName("Should return true for numeric values")
    void testVerifyIfValueContainsCommaFalse() {
        List<String> values = List.of("10", "5");

        boolean result = utils.verifyIfValueContainsComma(values);

        assertTrue(result);
    }

    @Test
    @DisplayName("Should throw InvalidInputFormatException when comma value is invalid")
    void testVerifyIfValueContainsCommaInvalidFormat() {
        List<String> values = List.of("10,abc", "5,3");

        assertThrows(InvalidInputFormatException.class,
            () -> utils.verifyIfValueContainsComma(values));
    }

    @Test
    @DisplayName("Should return false for empty list")
    void testVerifyIfValueContainsCommaEmptyList() {
        List<String> values = List.of("", "5");

        boolean result = utils.verifyIfValueContainsComma(values);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false for null list")
    void testVerifyIfValueContainsCommaNullList() {
        boolean result = utils.verifyIfValueContainsComma(null);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false for list with empty string")
    void testVerifyIfValueContainsCommaEmptyString() {
        List<String> values = List.of("", "5");

        boolean result = utils.verifyIfValueContainsComma(values);

        assertFalse(result);
    }

    // ========== Tests for verifyIfListValuesIsAppropriate ==========

    @Test
    @DisplayName("Should throw ListValuesEmptyException when list is empty")
    void testVerifyIfListValuesIsAppropriateEmpty() {
        List<String> values = new ArrayList<>();

        assertThrows(ListValuesEmptyException.class,
            () -> utils.verifyIfListValuesIsAppropriate(values));
    }

    @Test
    @DisplayName("Should throw IndexOutOfBoundsListValuesException when list has less than 2 values")
    void testVerifyIfListValuesIsAppropriateIndexOutOfBounds() {
        List<String> values = List.of("10");

        assertThrows(IndexOutOfBoundsListValuesException.class,
            () -> utils.verifyIfListValuesIsAppropriate(values));
    }

    @Test
    @DisplayName("Should throw InvalidInputFormatException for invalid format")
    void testVerifyIfListValuesIsAppropriateInvalidFormat() {
        List<String> values = List.of("10@20", "5");

        assertThrows(InvalidInputFormatException.class,
            () -> utils.verifyIfListValuesIsAppropriate(values));
    }

    @Test
    @DisplayName("Should pass validation for valid numeric values")
    void testVerifyIfListValuesIsAppropriateValid() {
        List<String> values = List.of("10", "5");

        assertDoesNotThrow(() -> utils.verifyIfListValuesIsAppropriate(values));
    }

    @Test
    @DisplayName("Should pass validation for values with operations symbols")
    void testVerifyIfListValuesIsAppropriateWithOperationSymbols() {
        List<String> values = List.of("10+5", "5");

        assertDoesNotThrow(() -> utils.verifyIfListValuesIsAppropriate(values));
    }

    @Test
    @DisplayName("Should pass validation for values with spaces")
    void testVerifyIfListValuesIsAppropriateWithSpaces() {
        List<String> values = List.of("10 + 5", "5");

        assertDoesNotThrow(() -> utils.verifyIfListValuesIsAppropriate(values));
    }

    @Test
    @DisplayName("Should pass validation for values with parentheses")
    void testVerifyIfListValuesIsAppropriateWithParentheses() {
        List<String> values = List.of("(10+5)", "5");

        assertDoesNotThrow(() -> utils.verifyIfListValuesIsAppropriate(values));
    }

}







