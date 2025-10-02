package org.carlossanchez.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.carlossanchez.model.ApiExternaData;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/tipo-cambio")
@RegisterRestClient(baseUri = "https://free.e-api.net.pe")
@Produces(MediaType.APPLICATION_JSON)
public interface Free_e_api_net_pe {
    @GET
    @Path("/{fecha}.json")
    ApiExternaData getTipoDeCambio(@PathParam("fecha") String fecha);
}
