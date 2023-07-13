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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestResponse;

import io.dborrego.domain.Invoice;
import io.dborrego.service.InvoiceService;

@Path("/invoice")
public class InvoiceHandler {

    @Inject
    InvoiceService invoiceService;

    @GET
    public List<Invoice> list() {
        return invoiceService.listAll();
    }

    @GET
    @Path("/{id}")
    public Invoice get(Long id) {
        return invoiceService.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Invoice create(Invoice parking) {
        return invoiceService.saveOrUpdate(parking);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Void> update(Invoice parking) {
        try {
            invoiceService.saveOrUpdate(parking);
        } catch (Exception e) {
            return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return RestResponse.status(Response.Status.OK);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Void> delete(Long id) {
        if (invoiceService.deleteById(id))
            return RestResponse.status(Response.Status.OK);
        else
            return RestResponse.status(Response.Status.NOT_FOUND);
    }

}