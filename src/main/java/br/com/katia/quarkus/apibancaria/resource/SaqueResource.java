package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.dto.OperacaoDTO;
import br.com.katia.quarkus.apibancaria.service.TransacaoService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/saques")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SaqueResource {

    @Inject
    TransacaoService service;

    @POST
    public String sacar(OperacaoDTO dto) {
        service.sacar(dto.contaId(), dto.valor());
        return "Saque realizado com sucesso";
    }
}