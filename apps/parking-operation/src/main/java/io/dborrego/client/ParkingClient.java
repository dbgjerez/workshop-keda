package io.dborrego.client;

import java.util.Set;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

import io.dborrego.domain.ParkingDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/parking")
@RegisterRestClient(configKey = "client-parking")
public interface ParkingClient {

    @GET
    public Set<ParkingDTO> findLastByPlateParked(@RestQuery("plate") String plate, @RestQuery("parked") Boolean parked);

    @PUT
    public void update(ParkingDTO parking, @QueryParam("id") Long id);

    @POST
    public void create(ParkingDTO parking);

}
