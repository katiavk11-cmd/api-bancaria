package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.dto.OperacaoDTO;
import br.com.katia.quarkus.apibancaria.dto.TransferenciaDTO;
import br.com.katia.quarkus.apibancaria.entity.Transacao;
import br.com.katia.quarkus.apibancaria.service.TransacaoService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/transacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransacaoResource {

    @Inject
    TransacaoService service;

    @POST
    @Path("/depositos")
    public String depositar(OperacaoDTO dto) {
        service.depositar(dto.contaId(), dto.valor());
        return "{\"msg\":\"Depósito realizado com sucesso\"}";
    }

    @POST
    @Path("/saques")
    public String sacar(OperacaoDTO dto) {
        service.sacar(dto.contaId(), dto.valor());
        return "{\"msg\":\"Saque realizado com sucesso\"}";
    }

    @POST
    @Path("/transferencias")
    public String transferir(TransferenciaDTO dto) {
        service.transferir(dto.contaOrigemId(), dto.contaDestinoId(), dto.valor());
        return "{\"msg\":\"Transferência realizada com sucesso\"}";
    }

    @GET
    @Path("/extrato/{contaId}")
    public List<Transacao> extrato(@PathParam("contaId") Long contaId) {
        return Transacao.list(
                "contaOrigemId = ?1 or contaDestinoId = ?1 order by dataHora desc",
                contaId
        );
    }
}