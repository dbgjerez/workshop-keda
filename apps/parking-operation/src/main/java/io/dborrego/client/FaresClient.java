package io.dborrego.client;

import java.util.Set;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

import io.dborrego.domain.FareDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/fares")
@RegisterRestClient(configKey = "client-fares")
public interface FaresClient {

    @GET
    public Set<FareDTO> getByVehicleType(@RestQuery String vehicleType);

}
