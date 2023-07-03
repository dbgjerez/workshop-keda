package io.dborrego.processor;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.dborrego.domain.Book;
import io.dborrego.domain.Stock;
import io.dborrego.service.BookService;

@ApplicationScoped
public class StockConsumer {

    private static final Logger LOGGER = Logger.getLogger(StockConsumer.class.getName());

    @Inject
    BookService bookService;

    @Incoming("stock")
    @Transactional
    public void update(final Stock stock) {
        if (stock != null) {
            LOGGER.info(String.format("Received %s", stock));
            if (stock.getType().equals("book"))
                bookService.saveOrUpdate(map(stock));
        }
    }

    public Book map(final Stock s) {
        final Book b = new Book();
        b.setIdBook(Long.valueOf(s.getId()));
        b.setTitle(s.getTitle());
        b.setStock(s.getQuantity());
        return b;
    }

}