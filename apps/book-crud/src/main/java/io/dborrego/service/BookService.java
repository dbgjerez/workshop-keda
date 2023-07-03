package io.dborrego.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import io.dborrego.domain.Book;
import io.dborrego.domain.BookRepository;

@ApplicationScoped
public class BookService {

    @Inject
    BookRepository bookRepository;

    public List<Book> listAll() {
        return bookRepository.listAll();
    }

    public Book findById(final Long idBook) {
        return bookRepository.findById(idBook);
    }

    @Transactional
    public Book saveOrUpdate(final Book book) {
        final Book b = Optional.ofNullable(book).map(Book::getIdBook).map(id -> bookRepository.findById(id))
                .orElse(new Book());
        b.setTitle(book.getTitle());
        b.setStock(book.getStock() + b.getStock());
        bookRepository.persist(b);
        return b;
    }

    @Transactional
    public Boolean deleteById(final Long id) {
        return Optional.ofNullable(id).map(idBook -> bookRepository.deleteById(idBook)).orElse(false);
    }

}
