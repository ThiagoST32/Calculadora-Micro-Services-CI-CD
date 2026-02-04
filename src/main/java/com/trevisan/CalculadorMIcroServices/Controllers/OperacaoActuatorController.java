package com.trevisan.CalculadorMIcroServices.Controllers;

import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperacaoResponseDTO;
import com.trevisan.CalculadorMIcroServices.infra.Actuator.ExternalServiceHealthIndicator;
import com.trevisan.CalculadorMIcroServices.infra.Feign.OperacaoPersistApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.health.contributor.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/calc/infos")
public class OperacaoActuatorController {

    private final ExternalServiceHealthIndicator healthIndicator;

    @Autowired
    public OperacaoActuatorController(ExternalServiceHealthIndicator healthIndicator) {
        this.healthIndicator = healthIndicator;
    }

    @GetMapping("/actuator/health")
    public ResponseEntity<Health> checkHealthOperations(){
        return new ResponseEntity<>(healthIndicator.health(), HttpStatus.OK);
    }


}
