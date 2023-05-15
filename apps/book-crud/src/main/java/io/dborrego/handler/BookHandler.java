package io.dborrego.handler;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestResponse;

import io.dborrego.domain.Book;
import io.dborrego.service.BookService;

@Path("/book")
public class BookHandler {

    @Inject
    BookService bookService;

    @GET
    public List<Book> list() {
        return bookService.listAll();
    }

    @GET
    @Path("/{id}")
    public Book get(Long id) {
        return bookService.findById(id);
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book create(Book book) {
        return bookService.saveOrUpdate(book);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Void> delete(Long id) {
        if (bookService.deleteById(id))
            return RestResponse.status(Response.Status.OK);
        else
            return RestResponse.status(Response.Status.NOT_FOUND);
    }

}