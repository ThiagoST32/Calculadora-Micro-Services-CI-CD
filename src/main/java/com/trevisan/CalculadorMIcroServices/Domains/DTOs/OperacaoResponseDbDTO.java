package com.trevisan.CalculadorMIcroServices.Domains.DTOs;

import com.trevisan.CalculadorMIcroServices.Domains.Enums.TipoDeOperacao;

public record OperacaoResponseDbDTO (String valueOne, String valueTwo, String result, TipoDeOperacao tipoDeOperacao){
}
