package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.entity.Cliente;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @GET
    public List<Cliente> listarTodos() {
        return Cliente.listAll();
    }
}