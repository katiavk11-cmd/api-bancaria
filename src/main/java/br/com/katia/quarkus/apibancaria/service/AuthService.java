package br.com.katia.quarkus.apibancaria.service;

import br.com.katia.quarkus.apibancaria.dto.LoginRequest;
import br.com.katia.quarkus.apibancaria.dto.TokenResponse;
import br.com.katia.quarkus.apibancaria.entity.Cliente;
import br.com.katia.quarkus.apibancaria.repository.ClienteRepository;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.mindrot.jbcrypt.BCrypt;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class AuthService {

    @Inject
    ClienteRepository clienteRepository;

    public TokenResponse login(LoginRequest request) {

        Cliente cliente = clienteRepository
                .find("email", request.email().trim())
                .firstResult();

        if (cliente == null) {
            throw new WebApplicationException("Usuário não encontrado", 404);
        }

        boolean senhaOk = BCrypt.checkpw(request.senha(), cliente.senha);

        if (!senhaOk) {
            throw new WebApplicationException("Senha inválida", 401);
        }

        String role = cliente.role;

        String token = Jwt.issuer("banco")
                .subject(cliente.email)
                .upn(cliente.email)
                .groups(Set.of(role))
                .claim("userId", cliente.id)
                .claim("nome", cliente.nome)
                .expiresIn(Duration.ofHours(2))
                .sign();

        return new TokenResponse(
                token,
                cliente.email,
                cliente.nome,
                role
        );
    }
}