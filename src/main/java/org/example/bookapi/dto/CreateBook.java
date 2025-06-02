package org.example.bookapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateBook(
        @NotBlank(message = "Author is required")
        String author,

        @NotBlank(message = "Title is required")
        String title,

        @Size(max = 1000, message = "Description must be at most 1000 characters")
        @NotBlank(message = "Description is required")
        String description,

        @NotBlank(message = "ISBN is required")
        @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN must be 10 or 13 digits")
        String isbn,

        @NotBlank(message = "Genre is required")
        String genre,

        @NotNull(message = "Release date is required")
        @Past(message = "Release date must be in the past")
        LocalDate releaseDate
) {}
