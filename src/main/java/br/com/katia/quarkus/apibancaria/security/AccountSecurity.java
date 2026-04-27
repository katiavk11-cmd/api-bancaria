package br.com.katia.quarkus.apibancaria.security;

import br.com.katia.quarkus.apibancaria.entity.Conta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.ContainerRequestContext;

@ApplicationScoped
public class AccountSecurity {

    public void validarAcessoConta(ContainerRequestContext ctx, Conta conta) {

        if (conta == null) {
            throw new RuntimeException("Conta inválida");
        }

        if (ctx == null) {
            throw new RuntimeException("Contexto de requisição inválido");
        }

        // FUTURO: validação JWT (role + userId)
        // String userId = ctx.getHeaderString("userId");
        // String role = ctx.getHeaderString("role");

        // regra mínima atual: só garante que existe conta
    }
}
