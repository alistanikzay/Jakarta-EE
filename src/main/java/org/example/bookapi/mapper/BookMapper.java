package org.example.bookapi.mapper;

import org.example.bookapi.Exception.ValidationException;
import org.example.bookapi.dto.CreateBookDTO;
import org.example.bookapi.dto.UpdateBookDTO;
import org.example.bookapi.dto.BookDTO;
import org.example.bookapi.entity.Book;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        if (book == null) return null;
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getPublicationDate(),
                book.getIsbn()
        );
    }

    public static Book toEntity(CreateBookDTO createBook) {
        if (createBook == null) return null;
        return new Book(
                null,
                createBook.title(),
                createBook.author(),
                createBook.description(),
                createBook.publicationDate(),
                createBook.isbn()
                // genre saknas i CreateBookDTO, sätts som null här
        );
    }

    public static void updateEntity(UpdateBookDTO updateBook, Book book) {
        if (updateBook == null) {
            throw new ValidationException("UpdateBookDTO cannot be null when updating an entity.");
        }
        if (updateBook == null || book == null) return;

        if (updateBook.title() != null) book.setTitle(updateBook.title());
        if (updateBook.author() != null) book.setAuthor(updateBook.author());
        if (updateBook.description() != null) book.setDescription(updateBook.description());
        if (updateBook.publicationDate() != null) book.setPublicationDate(updateBook.publicationDate());
        if (updateBook.isbn() != null) book.setIsbn(updateBook.isbn());
    }

    public static BookDTO mapToResponse(Book book) {
        return toDTO(book);
    }
}
