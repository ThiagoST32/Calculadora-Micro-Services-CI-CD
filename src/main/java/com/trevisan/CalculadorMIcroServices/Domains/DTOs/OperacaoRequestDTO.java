package com.trevisan.CalculadorMIcroServices.Domains.DTOs;

import com.trevisan.CalculadorMIcroServices.Domains.Enums.TipoDeOperacao;

public record OperacaoRequestDTO(String valor1, String valor2, TipoDeOperacao tipoDeOperacao) {
}
