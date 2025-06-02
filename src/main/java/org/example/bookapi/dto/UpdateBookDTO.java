package org.example.bookapi.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateBookDTO(
        @Size(max = 255, message = "Title must be at most 255 characters")
        String title,

        @Size(max = 255, message = "Author must be at most 255 characters")
        String author,

        @Size(max = 1000, message = "Description must be at most 1000 characters")
        String description,

        @Past(message = "Publication date must be in the past")
        LocalDate publicationDate,

        @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN must be 10 or 13 digits")
        String isbn
) {
    public int year() {
        return publicationDate != null ? publicationDate.getYear() : 0;
    }
}
