package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.model.Cliente;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/clientes")
public class OlaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> listar() {
        // Se o banco estiver conectado, isso retornará os dados do seu bancada.sql
        return Cliente.listAll();
    }
}