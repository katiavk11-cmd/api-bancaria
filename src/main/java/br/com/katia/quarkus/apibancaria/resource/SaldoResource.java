package br.com.katia.quarkus.apibancaria.resource;

import br.com.katia.quarkus.apibancaria.model.Cliente;
import br.com.katia.quarkus.apibancaria.model.LoggedUser;
import br.com.katia.quarkus.apibancaria.model.ViewSaldo;
import br.com.katia.quarkus.apibancaria.service.CurrentUserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/saldos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SaldoResource {

    @Inject
    CurrentUserService currentUserService; // Interface que criamos agora

    @GET
    @RolesAllowed({"GERENTE", "CLIENTE"})
    public Response listarSaldos() {
        LoggedUser usuario = currentUserService.getLoggedUser();

        // Log para saber quem está tentando ver
        System.out.println("👤 Usuário logado: " + usuario.email() + " | Role: " + usuario.role());

        // Teste de Sanidade: Quantos clientes existem?
        System.out.println("👥 Total de Clientes no banco: " + Cliente.count());

        // Teste de Sanidade: Quantos registros existem na View?
        System.out.println("💰 Total de registros em ViewSaldo: " + ViewSaldo.count());

        if (usuario.isAdmin()) {
            return Response.ok(ViewSaldo.listAll()).build();
        }

        return Response.ok(ViewSaldo.list("clienteId", usuario.id())).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"GERENTE", "CLIENTE"})
    public Response consultarSaldoPorId(@PathParam("id") Long id) {
        LoggedUser usuario = currentUserService.getLoggedUser();
        ViewSaldo saldo = ViewSaldo.findById(id);

        if (saldo == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Saldo não encontrado"))
                    .build();
        }

        // Validação de Propriedade (O ponto crucial contra o 403)
        // Se não for gerente e tentar ver saldo de outro ID, bloqueia!
        if (!usuario.isAdmin() && !saldo.clienteId.equals(usuario.id())) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Você não tem permissão para ver este saldo"))
                    .build();
        }

        return Response.ok(saldo).build();
    }

    // Classe interna simples para mensagens de erro (Estilo Ada)
    public static record ErrorResponse(String message) {}
}