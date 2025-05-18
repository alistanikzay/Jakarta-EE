package org.example.bookapi.mapper;

import org.example.bookapi.dto.BookDTO;
import org.example.bookapi.dto.CreateBookDTO;
import org.example.bookapi.dto.UpdateBookDTO;
import org.example.bookapi.entity.Book;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        if (book == null) return null;
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setPublicationDate(book.getPublicationDate());
        dto.setIsbn(book.getIsbn());
        return dto;
    }

    public static Book toEntity(CreateBookDTO dto) {
        return new Book(
                null,
                dto.getTitle(),
                dto.getAuthor(),
                dto.getDescription(),
                dto.getPublicationDate(),
                dto.getIsbn()
        );
    }

    public static void updateEntity(Book book, UpdateBookDTO dto) {
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setDescription(dto.getDescription());
        book.setPublicationDate(dto.getPublicationDate());
        book.setIsbn(dto.getIsbn());
    }
}
