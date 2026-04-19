package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.model.Cliente;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @GET
    @RolesAllowed("GERENTE")
    public List<Cliente> listarTodos() {
        return Cliente.listAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("GERENTE")
    public Response buscarPorId(@PathParam("id") Long id) {
        Cliente cliente = Cliente.findById(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Cliente não encontrado.\"}").build();
        }
        return Response.ok(cliente).build();
    }

    @POST
    @Transactional
    @RolesAllowed("GERENTE")
    public Response criar(Cliente cliente) {
        if (Cliente.find("email", cliente.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"E-mail já cadastrado.\"}").build();
        }

        String senhaHashed = BCrypt.hashpw(cliente.getSenha(), BCrypt.gensalt());
        cliente.setSenha(senhaHashed);
        cliente.persist();

        cliente.setSenha(null); // Protege a senha no retorno
        return Response.status(Response.Status.CREATED).entity(cliente).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("GERENTE")
    public Response atualizar(@PathParam("id") Long id, Cliente clienteAtualizado) {
        Cliente entidade = Cliente.findById(id);
        if (entidade == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (!entidade.getCpf().equals(clienteAtualizado.getCpf())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"O CPF não pode ser atualizado.\"}").build();
        }

        entidade.setNome(clienteAtualizado.getNome());
        entidade.setEmail(clienteAtualizado.getEmail());

        if (clienteAtualizado.getSenha() != null && !clienteAtualizado.getSenha().isEmpty()) {
            entidade.setSenha(BCrypt.hashpw(clienteAtualizado.getSenha(), BCrypt.gensalt()));
        }

        return Response.ok(entidade).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("GERENTE")
    public Response excluir(@PathParam("id") Long id) {
        Cliente entidade = Cliente.findById(id);
        if (entidade == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entidade.delete();
        return Response.ok("{\"message\": \"Cliente " + id + " deletado com sucesso!\"}").build();
    }
}