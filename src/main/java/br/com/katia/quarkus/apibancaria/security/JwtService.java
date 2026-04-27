package br.com.katia.quarkus.apibancaria.security;

import br.com.katia.quarkus.apibancaria.entity.Cliente;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtService {

    public String generate(Cliente cliente) {

        return Jwt.issuer("banco")
                .subject(cliente.email)
                .groups(Set.of(cliente.role)) // CORRIGIDO
                .claim("userId", cliente.id)
                .claim("email", cliente.email)
                .claim("nome", cliente.nome)
                .expiresIn(Duration.ofHours(1))
                .sign();
    }
}