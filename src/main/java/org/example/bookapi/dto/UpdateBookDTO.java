package org.example.bookapi.dto;

import java.time.LocalDate;

public class UpdateBookDTO {
    private String title;
    private String author;
    private String description;
    private LocalDate publicationDate;
    private String isbn;

    public UpdateBookDTO(String title, String author, String description, LocalDate publicationDate, String isbn) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String title() {
        return title;
    }

    public String author() {
        return author;

    }

    public int year() {
        return publicationDate != null ? publicationDate.getYear() : 0;
    }
}
