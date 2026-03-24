package com.trevisan.CalculadorMIcroServices.Utils;

import com.trevisan.CalculadorMIcroServices.Domains.Enums.TipoDeOperacao;
import com.trevisan.CalculadorMIcroServices.Exceptions.IndexOutOfBoundsListValuesException;
import com.trevisan.CalculadorMIcroServices.Exceptions.InvalidInputFormatException;
import com.trevisan.CalculadorMIcroServices.Exceptions.ListValuesEmptyException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.math.RoundingMode.HALF_UP;

@Component
public class OperacoesUtils {

    public Float mapValuesToFloat(List<String> values, TipoDeOperacao tipoDeOperacao){
        List<Float> valuesParsedToFloat = new ArrayList<>();
        valuesParsedToFloat.add(Float.parseFloat(values.getFirst()));
        return calculoComNumerosComVirgula(valuesParsedToFloat, tipoDeOperacao);
    }

    public float calculoComNumerosComVirgula(List<Float> values, TipoDeOperacao tipoDeOperacao){
        float result = 0F;
        float firstValueExtracted = values.getFirst();
        float lastValueExtracted = values.getLast();
        switch (tipoDeOperacao){
            case SOMA -> result = Float.sum(firstValueExtracted, lastValueExtracted);

            case DIVISAO -> result = firstValueExtracted / lastValueExtracted;

            case SUBTRACAO -> result = firstValueExtracted - lastValueExtracted;

            case MULTIPLICACAO -> result = firstValueExtracted * lastValueExtracted;
        }
        return result;
    }

    public BigDecimal calculoComNumerosDecimais(List<String> values, TipoDeOperacao tipoDeOperacao){
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal firstValueExtracted = new BigDecimal(values.getFirst());
        BigDecimal lastValueExtracted = new BigDecimal(values.getLast());
        switch (tipoDeOperacao){
            case SOMA -> result = firstValueExtracted.add(lastValueExtracted).setScale(2, HALF_UP);

            case DIVISAO -> result = firstValueExtracted.divide(lastValueExtracted, 2, HALF_UP);

            case SUBTRACAO -> result = firstValueExtracted.subtract(lastValueExtracted).setScale(2, HALF_UP);

            case MULTIPLICACAO -> result = firstValueExtracted.multiply(lastValueExtracted).setScale(2, HALF_UP);
        }
        return result;
    }

    public boolean verifyIfValueContaisDecimal(List<String> values){
        List<BigDecimal> valuesParsed = values.stream().map(BigDecimal::new).toList();
        return valuesParsed
                .stream()
                .anyMatch(bd -> bd.compareTo(
                        new BigDecimal(values.getFirst()).setScale(
                                bd.scale(), HALF_UP
                        )) == 0
                );
    }

    public boolean verifyIfValueContainsComma(List<String> values){
        if (values == null || values.getFirst().trim().isEmpty() || values.isEmpty()){
            return false;
        };

        for (String strValues : values){
            String normalizedValue = strValues.trim();
            try {
                if (normalizedValue.contains(",")){
                    Float.parseFloat(normalizedValue);
                }
                return true;
            } catch (NumberFormatException e){
                throw new InvalidInputFormatException();
            }
        }
        return false;
    }

    public void verifyIfListValuesIsAppropriate(List<String> values){
        if (values.isEmpty()){
            throw new ListValuesEmptyException();
        }

        if (values.size() < 2){
            throw new IndexOutOfBoundsListValuesException();
        }

        var valueExtract = values.getFirst();
        String regexToValidateInput = "^[\\d+\\-*/.() ]*$";
        if (!Pattern.matches(regexToValidateInput, valueExtract)){
            throw new InvalidInputFormatException();
        }
//        Implementar futuramente uma função para múltiplas operações
//        ‘String’ regexToValidateExpression = "";
//        if (!Pattern.matches(regexToValidateExpression, va))
    }
}
