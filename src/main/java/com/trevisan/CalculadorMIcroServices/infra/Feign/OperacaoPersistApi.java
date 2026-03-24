package com.trevisan.CalculadorMIcroServices.infra.Feign;

import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperacaoResponseDTO;
import com.trevisan.CalculadorMIcroServices.Domains.DTOs.OperationRequestPersistDTO;
import com.trevisan.CalculadorMIcroServices.infra.Config.ConfigFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "operationsPersist", url = "${feign.client.calcRepo.url}", configuration = ConfigFeign.class)
public interface OperacaoPersistApi {

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    void persistNewOperation(OperationRequestPersistDTO dto);

    @RequestMapping(method = RequestMethod.GET, value = "")
    List<OperacaoResponseDTO> getOperations();

    @RequestMapping(method = RequestMethod.GET, value = "getPreviousOperation")
    OperacaoResponseDTO getPreviousOperation();

}
