package br.com.katia.quarkus.apibancaria.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Conta extends PanacheEntity {

    public String numero;

    @Enumerated(EnumType.STRING)
    public TipoConta tipo;

    public BigDecimal saldo;

    @ManyToOne
    public Cliente cliente;

    @Version
    public Long version;
}