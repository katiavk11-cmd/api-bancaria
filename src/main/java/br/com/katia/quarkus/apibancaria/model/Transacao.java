package br.com.katia.quarkus.apibancaria.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacao")
public class Transacao extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TipoTransacao tipo;

    @Column(nullable = false, precision = 15, scale = 2)
    public BigDecimal valor;

    @Column(name = "data_hora", nullable = false)
    public LocalDateTime dataHora = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "conta_origem_id")
    public Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id")
    public Conta contaDestino;

    public Transacao() {}
}