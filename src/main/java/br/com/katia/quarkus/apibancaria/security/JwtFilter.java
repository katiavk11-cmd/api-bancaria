package br.com.katia.quarkus.apibancaria.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String auth = requestContext.getHeaderString("Authorization");

        // NÃO faça validação aqui.
        // O Quarkus SmallRye JWT já faz isso automaticamente.

        // Este filtro deve ficar vazio ou ser removido.
    }
}