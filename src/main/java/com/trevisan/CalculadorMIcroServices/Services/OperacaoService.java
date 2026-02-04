package com.trevisan.CalculadorMIcroServices.Services;

import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperacaoRequestDTO;
import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperacaoResponseDTO;
import com.trevisan.CalculadorMIcroServices.Utils.OperacoesUtils;
import com.trevisan.CalculadorMIcroServices.infra.Feign.OperacaoPersistApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperacaoService {

    private final OperacoesUtils utils;
    private final OperacaoPersistApi persistApi;

    @Autowired
    public OperacaoService(OperacoesUtils utils, OperacaoPersistApi persistApi) {
        this.utils = utils;
        this.persistApi = persistApi;
    }

    public OperacaoResponseDTO calculoOperacao(OperacaoRequestDTO operaDTO){
        List<String> values = new ArrayList<>();
        values.add(operaDTO.valor1());
        values.add(operaDTO.valor2());
        
        utils.verifyIfListValuesIsAppropriate(values);
        var tipoDeOperacao = operaDTO.tipoDeOperacao();

        String result = "";


        if (utils.verifyIfValueContainsComma(values)){
            result = String.valueOf(utils.mapValuesToFloat(values, tipoDeOperacao));
        }

        if (utils.verifyIfValueContaisDecimal(values)){
            result = String.valueOf(utils.calculoComNumerosDecimais(values, tipoDeOperacao));
        }

        persistApi.persistNewOperation(operaDTO);

        return new OperacaoResponseDTO(
                result
        );
    }
}
