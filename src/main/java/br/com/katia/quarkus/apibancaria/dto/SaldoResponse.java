package br.com.katia.quarkus.apibancaria.dto;

import java.math.BigDecimal;

public class SaldoResponse {
    public BigDecimal saldo;

    public SaldoResponse(BigDecimal saldo) {
        this.saldo = saldo;
    }
}