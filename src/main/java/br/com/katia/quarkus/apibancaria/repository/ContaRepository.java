package br.com.katia.quarkus.apibancaria.repository;

import br.com.katia.quarkus.apibancaria.entity.Conta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContaRepository implements PanacheRepository<Conta> {
}