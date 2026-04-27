package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.dto.OperacaoDTO;
import br.com.katia.quarkus.apibancaria.service.TransacaoService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/operacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OperacaoResource {

    @Inject
    TransacaoService service;

    @POST
    @Path("/deposito")
    public String depositar(OperacaoDTO dto) {
        service.depositar(dto.contaId(), dto.valor());
        return "Depósito realizado com sucesso";
    }

    @POST
    @Path("/saque")
    public String sacar(OperacaoDTO dto) {
        service.sacar(dto.contaId(), dto.valor());
        return "Saque realizado com sucesso";
    }
}