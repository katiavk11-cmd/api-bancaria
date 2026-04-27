package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.entity.Transacao;
import br.com.katia.quarkus.apibancaria.service.ExtratoService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Context;

import jakarta.ws.rs.container.ContainerRequestContext;

import java.util.List;

@Path("/extrato")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExtratoResource {

    @Inject
    ExtratoService service;

    @GET
    @Path("/conta/{id}")
    public List<Transacao> conta(
            @Context ContainerRequestContext ctx,
            @PathParam("id") Long id
    ) {
        return service.extratoConta(ctx, id);
    }

    @GET
    public List<Transacao> geral(@Context ContainerRequestContext ctx) {
        return service.extratoGeral(ctx);
    }
}