package br.com.katia.quarkus.apibancaria.dto;

public record TokenResponse(
        String token,
        String email,
        String nome,
        String role
) {}