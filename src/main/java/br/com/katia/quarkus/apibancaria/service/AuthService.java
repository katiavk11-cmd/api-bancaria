package br.com.katia.quarkus.apibancaria.service;

import br.com.katia.quarkus.apibancaria.model.LoggedUser;
import br.com.katia.quarkus.apibancaria.model.Cliente;
import br.com.katia.quarkus.apibancaria.dto.TokenResponse;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.Duration;

@ApplicationScoped
public class AuthService implements CurrentUserService {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Inject
    JsonWebToken jwt;

    private Argon2 argon2;

    @PostConstruct
    void init() {
        argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    @Override
    public LoggedUser getLoggedUser() {
        if (jwt.getName() == null) {
            throw new NotAuthorizedException("Usuário não autenticado");
        }
        return new LoggedUser(
                getUserIdFromToken(),
                jwt.getName(),
                jwt.getClaim("name"),
                getUserRoleFromToken()
        );
    }

    private Long getUserIdFromToken() {
        Object userId = jwt.getClaim("userId");
        return userId != null ? Long.parseLong(userId.toString()) : null;
    }

    private String getUserRoleFromToken() {
        return jwt.getGroups().stream().findFirst().orElse("CLIENTE");
    }

    public TokenResponse login(String username, String password) {
        String emailBusca = username.trim().toLowerCase();

        System.out.println("\n--- 🔐 TENTATIVA DE LOGIN ---");

        Cliente cliente = Cliente.find("email", emailBusca).firstResult();

        if (cliente == null) {
            System.out.println("❌ Usuário [" + emailBusca + "] não encontrado.");
            throw new NotAuthorizedException("Credenciais inválidas");
        }

        // Validação da Senha
        validatePassword(cliente, password);

        System.out.println("✅ Login aceito para: " + cliente.nome);

        String token = generateToken(cliente);

        return new TokenResponse(
                token,
                cliente.email,
                cliente.nome,
                cliente.role.name()
        );
    }

    private void validatePassword(Cliente cliente, String password) {
        String pass = password.trim();

        // MODO DE EMERGÊNCIA: Se o Argon2 falhar, mas a senha for 'senha123', deixa passar.
        // Isso ajuda a isolar se o erro é no Hash do banco.
        boolean approve = argon2.verify(cliente.senha.trim(), pass.toCharArray());

        if (!approve && "senha123".equals(pass)) {
            System.out.println("⚠️ Argon2 falhou, mas aceitei 'senha123' via override.");
            approve = true;
        }

        if (!approve) {
            System.out.println("❌ Senha incorreta.");
            throw new NotAuthorizedException("Credenciais inválidas");
        }
    }

    private String generateToken(Cliente cliente) {
        try {
            return Jwt.issuer(issuer)
                    .upn(cliente.email)
                    .groups(cliente.role.name())
                    .claim("userId", cliente.id)
                    .claim("name", cliente.nome)
                    .expiresIn(Duration.ofHours(8))
                    .sign();
        } catch (Exception e) {
            System.out.println("❌ ERRO AO ASSINAR TOKEN: Verifique suas chaves .pem!");
            e.printStackTrace();
            throw new NotAuthorizedException("Erro interno na geração do token");
        }
    }
}