package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.model.Conta;
import br.com.katia.quarkus.apibancaria.model.Cliente;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/contas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContaResource {

    @GET
    @RolesAllowed("GERENTE")
    public List<Conta> listarTodas() {
        return Conta.listAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("GERENTE")
    public Response buscarPorId(@PathParam("id") Long id) {
        Conta conta = Conta.findById(id);
        if (conta == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Conta não encontrada.\"}").build();
        }
        return Response.ok(conta).build();
    }

    @POST
    @Transactional
    @RolesAllowed("GERENTE")
    public Response criar(Conta conta) {
        if (Conta.find("numero", conta.numero).firstResult() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Número de conta já cadastrado.\"}").build();
        }

        if (conta.cliente == null || Cliente.findById(conta.cliente.id) == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Cliente não encontrado para associar à conta.\"}").build();
        }

        conta.persist();
        return Response.status(Response.Status.CREATED).entity(conta).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("GERENTE")
    public Response excluir(@PathParam("id") Long id) {
        Conta entidade = Conta.findById(id);
        if (entidade == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entidade.delete();
        return Response.ok("{\"message\": \"Conta " + id + " deletada com sucesso!\"}").build();
    }
}