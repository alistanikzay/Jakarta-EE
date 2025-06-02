package org.example.bookapi.dto;

import java.time.LocalDate;

public record BookDTO(
        Long id,
        String title,
        String author,
        String description,
        LocalDate publicationDate,
        String isbn
) {
    public int releaseYear() {
        return publicationDate != null ? publicationDate.getYear() : -1;
    }

    public LocalDate getPublished() {
        return publicationDate;
    }
}
