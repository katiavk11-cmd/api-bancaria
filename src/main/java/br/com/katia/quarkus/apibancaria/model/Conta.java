package br.com.katia.quarkus.apibancaria.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "conta")
public class Conta extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true)
    public String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TipoConta tipo;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    public Cliente cliente;

    // Construtor padrão para o Hibernate
    public Conta() {}
}