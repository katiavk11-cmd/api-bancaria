package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.dto.LoginRequest;
import br.com.katia.quarkus.apibancaria.dto.TokenResponse;
import br.com.katia.quarkus.apibancaria.service.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public TokenResponse login(LoginRequest request) {
        return authService.login(request);
    }
}