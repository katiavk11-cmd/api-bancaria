package br.com.katia.quarkus.apibancaria.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/debug")
public class DebugResource {

    @GET
    public String test() {
        return "API OK";
    }
}
