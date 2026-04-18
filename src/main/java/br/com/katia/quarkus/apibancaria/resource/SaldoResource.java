package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.model.ViewSaldo;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/saldos")
@Produces(MediaType.APPLICATION_JSON)
public class SaldoResource {

    @GET
    public List<ViewSaldo> listarSaldos() {
        return ViewSaldo.listAll();
    }
}