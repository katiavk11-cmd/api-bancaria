package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.model.Transacao;
import br.com.katia.quarkus.apibancaria.model.Conta;
import br.com.katia.quarkus.apibancaria.model.TipoConta;
import br.com.katia.quarkus.apibancaria.model.TipoTransacao;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/transacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransacaoResource {

    @GET
    @PermitAll
    public List<Transacao> listar() {
        return Transacao.listAll();
    }

    @POST
    @Transactional
    @PermitAll
    public Response criar(Transacao transacao) {
        if (transacao.tipo == TipoTransacao.SAQUE || transacao.tipo == TipoTransacao.DEPOSITO) {
            Conta conta = transacao.tipo == TipoTransacao.SAQUE ?
                    Conta.findById(transacao.contaOrigem.id) : Conta.findById(transacao.contaDestino.id);

            if (conta == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Conta não encontrada.\"}").build();
            }

            if (conta.tipo == TipoConta.ELETRONICA) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Conta ELETRONICA só permite transferências.\"}").build();
            }
        }

        if (transacao.tipo == TipoTransacao.TRANSFERENCIA) {
            if (transacao.contaOrigem == null || transacao.contaDestino == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Transferência exige conta de origem e destino.\"}").build();
            }
        }

        transacao.persist();
        return Response.status(Response.Status.CREATED).entity(transacao).build();
    }
}