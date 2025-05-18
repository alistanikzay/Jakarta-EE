package org.example.bookapi.Repository;

import org.example.bookapi.entity.Book;

import java.time.LocalDate;

public class S extends Book {

    public S(Long id, String title, String author, String description, LocalDate publicationDate, String isbn) {
        super(id, title, author, description, publicationDate, isbn);
    }

    public Long getId() {
        return null;
    }

    public void setId(long l) {
    }
}
