package org.carlossanchez.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.carlossanchez.api.Free_e_api_net_pe;
import org.carlossanchez.model.ConsultaTipoDeCambio;
import org.carlossanchez.model.ApiExternaData;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.util.Map;

@ApplicationScoped
public class ConsultasDniService {

    @Inject
    @RestClient
    Free_e_api_net_pe apiCambio;

    @Transactional
    public Response getTipoDeCambio(String dni) {
        long consultas = ConsultaTipoDeCambio.count("dni = ?1 and fecha = ?2", dni, LocalDate.now());
        if (consultas >= 10) {
            return Response.status(429)
                    .entity(Map.of("Error", "Has superado el límite de 10 consultas diarias."))
                    .build();
        } else if (dni.length() < 8) {
            return Response.status(400)
                    .entity(Map.of("Error", "El DNI proporcionado no cumple el requisito."))
                    .build();
        }

        String fecha = LocalDate.now().toString();

        ApiExternaData api = apiCambio.getTipoDeCambio(fecha);

        ConsultaTipoDeCambio c = new ConsultaTipoDeCambio();
        c.dni = dni;
        c.fecha = LocalDate.now();
        c.compra = api.compra;
        c.venta = api.venta;
        c.sunat = api.sunat;
        c.persist();

        return Response.ok(Map.of(
                "dni", dni,
                "fecha", api.fecha != null ? api.fecha : fecha,
                "compra", c.compra,
                "venta", c.venta,
                "sunat", c.sunat
        )).build();
    }
}
