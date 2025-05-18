package org.example.bookapi.Repository;

import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import org.example.bookapi.entity.Book;

import java.util.*;
import java.util.stream.Stream;

public class InMemoryBookRepository implements BookRepository {

    private final Map<Long, Book> books = new HashMap<>();
    private long nextId = 1;

    @Override
    public <S extends Book> S save(S book) {
        if (book.getId() == null) {
            book.setId(nextId++);
        }
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public <S extends Book> List<S> saveAll(List<S> entities) {
        return entities.stream().map(this::save).toList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public Stream<Book> findAll() {
        return books.values().stream();
    }

    @Override
    public Page<Book> findAll(PageRequest pageRequest, Order<Book> sortBy) {
        return null; // Du behöver inte implementera för G-nivå om du inte använder det
    }

    @Override
    public void deleteById(Long id) {
        books.remove(id);
    }

    @Override
    public void delete(Book entity) {
        if (entity != null && entity.getId() != null) {
            books.remove(entity.getId());
        }
    }

    @Override
    public void deleteAll(List<? extends Book> entities) {
        for (Book book : entities) {
            delete(book);
        }
    }
}
