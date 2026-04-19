package br.com.katia.quarkus.apibancaria.service;

import br.com.katia.quarkus.apibancaria.dto.TokenResponse;
import br.com.katia.quarkus.apibancaria.model.Cliente;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotAuthorizedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.mindrot.jbcrypt.BCrypt;
import java.time.Duration;

@ApplicationScoped
public class AuthService {

    // Agora ele vai ler do seu properties: https://br.com.katia.banco/issuer
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public TokenResponse login(String email, String senha) {
        Cliente cliente = Cliente.find("email", email).firstResult();

        if (cliente == null || !BCrypt.checkpw(senha, cliente.getSenha())) {
            throw new NotAuthorizedException("E-mail ou senha incorretos.");
        }

        String token = Jwt.issuer(issuer) // Usa o SEU issuer
                .upn(cliente.getEmail())
                .groups(cliente.getRole().name())
                .claim("nome", cliente.getNome())
                .expiresIn(Duration.ofHours(8))
                .sign();

        return new TokenResponse(token, cliente.getEmail(), cliente.getNome(), cliente.getRole().name());
    }
}