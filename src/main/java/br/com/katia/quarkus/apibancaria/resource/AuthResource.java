package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.dto.LoginRequest;
import br.com.katia.quarkus.apibancaria.dto.TokenResponse;
import br.com.katia.quarkus.apibancaria.service.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @PermitAll
    @Transactional
    public TokenResponse login(@Valid LoginRequest request) {
        // Passamos os dados do Record para o Service
        return authService.login(request.username(), request.password());
    }
}