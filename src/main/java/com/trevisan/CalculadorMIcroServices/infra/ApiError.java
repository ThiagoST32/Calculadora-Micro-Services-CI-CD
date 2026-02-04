package com.trevisan.CalculadorMIcroServices.infra;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ApiError (
        LocalDateTime timestamp,

        int errorCode,

        String errorStatus,

        List<String> message)
{
}
