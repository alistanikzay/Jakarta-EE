package org.example.bookapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateBookDTO(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Author is required")
        String author,

        @Size(max = 1000, message = "Description must be at most 1000 characters")
        String description,

        @Past(message = "Publication date must be in the past")
        LocalDate publicationDate,

        @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN must be 10 or 13 digits")
        String isbn
) {

}
