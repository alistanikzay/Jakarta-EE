package org.example.bookapi.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UpdateBookDTO {

    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    @Size(max = 255, message = "Author must be at most 255 characters")
    private String author;

    @Size(max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    @Past(message = "Publication date must be in the past")
    private LocalDate publicationDate;

    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN must be 10 or 13 digits")
    private String isbn;


    public UpdateBookDTO() {}

    public UpdateBookDTO(String title, String author, String description, LocalDate publicationDate, String isbn) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
    }


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

    public String title() { return title; }
    public String author() { return author; }
    public int year() { return publicationDate != null ? publicationDate.getYear() : 0; }
}
