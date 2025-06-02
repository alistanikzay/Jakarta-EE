package org.example.bookapi.Repository;

import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.Order;
import org.example.bookapi.entity.Book;

import java.util.*;
import java.util.stream.Collectors;
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
        return entities.stream().map(this::save).collect(Collectors.toList());
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

        List<Book> sorted = books.values().stream()
                .sorted((b1, b2) -> b1.getId().compareTo(b2.getId()))
                .collect(Collectors.toList());

        return new Page<Book>() {
            @Override
            public Iterator<Book> iterator() {
                return null;
            }

            @Override
            public List<Book> content() {
                return List.of();
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public int numberOfElements() {
                return 0;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public PageRequest pageRequest() {
                return null;
            }

            @Override
            public PageRequest nextPageRequest() {
                return null;
            }

            @Override
            public PageRequest previousPageRequest() {
                return null;
            }

            @Override
            public boolean hasTotals() {
                return false;
            }

            @Override
            public long totalElements() {
                return 0;
            }

            @Override
            public long totalPages() {
                return 0;
            }

            public List<Book> items() {
                return sorted;
            }

            public int totalCount() {
                return sorted.size();
            }

            public int page() {
                return (int) pageRequest.page();
            }

            public int pageSize() {
                return (int) pageRequest.page();
            }
        };
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

    @Override
    public List<Book> findByAuthorContainingIgnoreCase(String author) {
        if (author == null) return Collections.emptyList();
        String lowerAuthor = author.toLowerCase();
        return books.values().stream()
                .filter(book -> book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerAuthor))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByTitleContainingIgnoreCase(String title) {
        if (title == null) return Collections.emptyList();
        String lowerTitle = title.toLowerCase();
        return books.values().stream()
                .filter(book -> book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerTitle))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(String author, String title) {
        if (author == null || title == null) return Collections.emptyList();
        String lowerAuthor = author.toLowerCase();
        String lowerTitle = title.toLowerCase();
        return books.values().stream()
                .filter(book -> book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerAuthor))
                .filter(book -> book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerTitle))
                .collect(Collectors.toList());
    }
}
