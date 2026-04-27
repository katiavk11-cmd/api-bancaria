package br.com.katia.quarkus.apibancaria.dto;

import java.math.BigDecimal;

public record ContaResponse(
        Long id,
        String numero,
        String tipo,
        BigDecimal saldo,
        Long clienteId
) {}