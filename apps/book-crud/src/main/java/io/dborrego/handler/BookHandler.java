package io.dborrego.handler;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import io.dborrego.domain.model.Book;
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
}
