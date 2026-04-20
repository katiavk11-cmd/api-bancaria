package br.com.katia.quarkus.apibancaria.dto;

public record TokenResponse(String token, String username, String name, String role) {}