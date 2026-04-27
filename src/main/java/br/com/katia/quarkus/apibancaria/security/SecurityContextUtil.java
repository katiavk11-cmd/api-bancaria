package br.com.katia.quarkus.apibancaria.security;

import jakarta.ws.rs.container.ContainerRequestContext;

import java.util.Set;

public class SecurityContextUtil {

    @SuppressWarnings("unchecked")
    public static Set<String> getRoles(ContainerRequestContext ctx) {
        return (Set<String>) ctx.getProperty("roles");
    }

    public static String getUser(ContainerRequestContext ctx) {
        return (String) ctx.getProperty("user");
    }
}