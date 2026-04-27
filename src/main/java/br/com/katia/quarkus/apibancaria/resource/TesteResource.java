package br.com.katia.quarkus.apibancaria.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/teste")
public class TesteResource {

    @GET
    @RolesAllowed("GERENTE")
    public String teste() {
        return "OK autenticado!";
    }
}