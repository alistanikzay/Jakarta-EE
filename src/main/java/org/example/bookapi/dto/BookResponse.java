package org.example.bookapi.dto;

import java.time.LocalDate;

public record BookResponse(
        Long id,
        String title,
        String author,
        String description,
        String isbn,
        String genre,
        LocalDate releaseDate
) {}
