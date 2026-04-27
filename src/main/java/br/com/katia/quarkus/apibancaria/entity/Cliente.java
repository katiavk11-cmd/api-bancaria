package br.com.katia.quarkus.apibancaria.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Cliente extends PanacheEntity {

    public String nome;
    public String email;
    public String senha;
    public String role; // ADMIN / CLIENTE
}