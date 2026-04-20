package br.com.katia.quarkus.apibancaria.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "O e-mail (username) é obrigatório")
        String username, // Na sua API, o usuário digita o e-mail aqui

        @NotBlank(message = "A senha (password) é obrigatória")
        String password
) {
}