package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.model.Cliente;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @GET
    @RolesAllowed("GERENTE") // Só o Bob vê a lista
    public List<Cliente> listarTodos() {
        return Cliente.listAll();
    }

    @POST
    @Transactional
    @PermitAll // Mudamos para PermitAll para você conseguir criar o PRIMEIRO gerente (Bob)
    public Response criar(Cliente cliente) {
        if (Cliente.find("email", cliente.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"E-mail já cadastrado.\"}").build();
        }

        // Criptografia obrigatória para o login funcionar depois
        String senhaHashed = BCrypt.hashpw(cliente.getSenha(), BCrypt.gensalt());
        cliente.setSenha(senhaHashed);

        cliente.persist();
        return Response.status(Response.Status.CREATED).entity(cliente).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("GERENTE")
    public Response excluir(@PathParam("id") Long id) {
        Cliente entidade = Cliente.findById(id);

        if (entidade == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro: Cliente não encontrado.\"}").build();
        }

        entidade.delete();

        // Mudamos de .noContent() para .ok() com um JSON de confirmação
        return Response.ok("{\"message\": \"Cliente " + id + " deletado com sucesso!\"}").build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("GERENTE")
    public Response atualizar(@PathParam("id") Long id, Cliente clienteAtualizado) {
        Cliente entidade = Cliente.findById(id);

        if (entidade == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Cliente não encontrado.\"}").build();
        }

        // REGRA DE OURO: Verifica se o CPF enviado é diferente do que está no banco
        if (!entidade.getCpf().equals(clienteAtualizado.getCpf())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"O CPF não pode ser atualizado.\"}").build();
        }

        // Se o CPF for igual, prossegue com as outras atualizações
        entidade.setNome(clienteAtualizado.getNome());
        entidade.setEmail(clienteAtualizado.getEmail());

        if (clienteAtualizado.getSenha() != null && !clienteAtualizado.getSenha().isEmpty()) {
            entidade.setSenha(BCrypt.hashpw(clienteAtualizado.getSenha(), BCrypt.gensalt()));
        }

        return Response.ok(entidade).build();
    }


}


