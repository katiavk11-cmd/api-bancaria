package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.dto.AuthRequest;
import br.com.katia.quarkus.apibancaria.dto.TokenResponse;
import br.com.katia.quarkus.apibancaria.service.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public TokenResponse login(AuthRequest auth) {
        // Agora delegamos a lógica para o Service, igual no projeto da Ada
        return authService.login(auth.email, auth.senha);
    }
}