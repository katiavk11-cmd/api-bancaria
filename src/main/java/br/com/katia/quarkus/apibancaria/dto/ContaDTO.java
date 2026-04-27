package br.com.katia.quarkus.apibancaria.dto;

import br.com.katia.quarkus.apibancaria.entity.TipoConta;
import java.math.BigDecimal;

public record ContaDTO(
        String numero,
        Long clienteId,
        TipoConta tipo,
        BigDecimal saldoInicial
) {}