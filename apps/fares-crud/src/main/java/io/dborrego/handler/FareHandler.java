package io.dborrego.handler;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestResponse;

import io.dborrego.domain.Fare;
import io.dborrego.service.FaresService;

@Path("/fares")
public class FareHandler {

    @Inject
    FaresService faresService;

    @GET
    public List<Fare> list(@QueryParam("vehicleType") String vehicleType) {
        return faresService.listAll(vehicleType);
    }

    @GET
    @Path("/{id}")
    public Fare get(Long id) {
        return faresService.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Fare create(Fare parking) {
        return faresService.saveOrUpdate(parking);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Void> update(Fare parking) {
        try {
            faresService.saveOrUpdate(parking);
        } catch (Exception e) {
            return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return RestResponse.status(Response.Status.OK);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Void> delete(Long id) {
        if (faresService.deleteById(id))
            return RestResponse.status(Response.Status.OK);
        else
            return RestResponse.status(Response.Status.NOT_FOUND);
    }

}