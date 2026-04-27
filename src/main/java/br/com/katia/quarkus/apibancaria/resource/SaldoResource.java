package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.entity.Conta;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.WebApplicationException;

import java.math.BigDecimal;

@Path("/saldos")
@Produces(MediaType.APPLICATION_JSON)
public class SaldoResource {

    @GET
    @Path("/{id}")
    public BigDecimal obterSaldo(@PathParam("id") Long id) {

        Conta conta = Conta.findById(id);

        if (conta == null) {
            throw new WebApplicationException("Conta não encontrada", 404);
        }

        return conta.saldo;
    }
}