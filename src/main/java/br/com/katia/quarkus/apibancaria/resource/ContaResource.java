package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.dto.ContaDTO;
import br.com.katia.quarkus.apibancaria.dto.ContaResponse;
import br.com.katia.quarkus.apibancaria.service.ContaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/contas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContaResource {

    @Inject
    ContaService service;

    @POST
    public ContaResponse criar(ContaDTO dto) {
        return service.criarConta(
                dto.numero(),
                dto.clienteId(),
                dto.tipo(),
                dto.saldoInicial()
        );
    }

    @GET
    public List<ContaResponse> listar() {
        return service.listarContas();
    }

    @GET
    @Path("/{id}")
    public ContaResponse buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }
}