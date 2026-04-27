package br.com.katia.quarkus.apibancaria.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transacao extends PanacheEntity {

    public String tipo;

    public BigDecimal valor;

    public LocalDateTime dataHora;

    public Long contaOrigemId;

    public Long contaDestinoId;
}