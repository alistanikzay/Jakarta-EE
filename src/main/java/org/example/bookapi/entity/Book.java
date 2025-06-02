package org.example.bookapi.entity;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class Book implements Comparable<Book> {

    private Long id;
    private String title;
    private String author;
    private String description;
    private LocalDate publicationDate;
    private String isbn;
    private String genre;
    private String language; // NYTT FÄLT

    // Konstruktor med genre
    public Book(Long id, String title, String author, String description,
                LocalDate publicationDate, String isbn, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }



    // Extra konstruktor utan genre (genre sätts till null)
    public Book(Long id, String title, String author, String description,
                LocalDate publicationDate, String isbn) {
        this(id, title, author, description, publicationDate, isbn, null);
    }

    // Getters och setters
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

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }
}
