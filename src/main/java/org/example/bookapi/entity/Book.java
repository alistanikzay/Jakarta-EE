package org.example.bookapi.entity;

import java.time.LocalDate;

public class Book implements Comparable<Book> {

    private Long id;
    private String title;
    private String author;
    private String description;
    private LocalDate publicationDate;
    private String isbn;
    private Object year;

    public Book(Long id, String title, String author, String description, LocalDate publicationDate, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
    }

    public Book(long id, String title, String author, int i) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationDate = LocalDate.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    public void setYear(Object year) {
        this.year = year;
    }

    public Object getYear() {
        return year;
    }
}
