package com.trevisan.CalculadorMIcroServices.Controllers;

import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperacaoRequestDTO;
import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperacaoResponseDTO;
import com.trevisan.CalculadorMIcroServices.Services.OperacaoService;
import com.trevisan.CalculadorMIcroServices.infra.Feign.OperacaoPersistApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/calc")
public class OperacaoController {

    private final OperacaoService service;
    private final OperacaoPersistApi persistApi;

    @Autowired
    public OperacaoController(OperacaoService service, OperacaoPersistApi persistApi) {
        this.service = service;
        this.persistApi = persistApi;
    }

    @PostMapping
    public ResponseEntity<OperacaoResponseDTO>callOperatingService(@RequestBody OperacaoRequestDTO dto){
        return new ResponseEntity<>(service.calculoOperacao(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OperacaoResponseDTO>>getAllOperations(){
        return new ResponseEntity<>(persistApi.getOperations(), HttpStatus.OK);
    }

    @GetMapping("/prev")
    public ResponseEntity<OperacaoResponseDTO>getPreviousOperation(){
        return new ResponseEntity<>(persistApi.getPreviousOperation(), HttpStatus.OK);
    }
}
