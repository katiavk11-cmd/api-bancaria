package br.com.katia.quarkus.apibancaria.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;

@Entity
@Immutable // Indica que o Java não pode deletar ou editar dados aqui, apenas ler
@Table(name = "view_saldo")
public class ViewSaldo extends PanacheEntityBase {

    @Id
    public Long id;
    public String numero;
    public String tipo;
    public BigDecimal saldo;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
}