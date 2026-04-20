package br.com.katia.quarkus.apibancaria.model;

public record LoggedUser(
        Long id,
        String email,
        String nome,
        String role
) {

public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role) || "GERENTE".equalsIgnoreCase(role);
    }
}