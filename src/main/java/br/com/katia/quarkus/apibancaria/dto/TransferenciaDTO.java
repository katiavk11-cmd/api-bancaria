package br.com.katia.quarkus.apibancaria.dto;

import java.math.BigDecimal;

public record TransferenciaDTO(
        Long contaOrigemId,
        Long contaDestinoId,
        BigDecimal valor
) {}