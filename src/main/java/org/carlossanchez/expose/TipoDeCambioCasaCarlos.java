package org.carlossanchez.expose;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.carlossanchez.service.ConsultasDniService;

import java.util.Map;

@Path("/tipo-cambio")
@Produces(MediaType.APPLICATION_JSON)
public class TipoDeCambioCasaCarlos {

    @Inject
    ConsultasDniService service;

    @GET
    public Response getTipoCambio(@QueryParam("dni") String dni) {
        if (dni == null || dni.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("Error", "El número de DNI es obligatorio.")).build();
        }
        return service.getTipoDeCambio(dni);
    }
}
