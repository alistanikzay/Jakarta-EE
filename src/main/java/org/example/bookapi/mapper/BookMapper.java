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

        if (updateBook.getTitle() != null) book.setTitle(updateBook.getTitle());
        if (updateBook.getAuthor() != null) book.setAuthor(updateBook.getAuthor());
        if (updateBook.getDescription() != null) book.setDescription(updateBook.getDescription());
        if (updateBook.getPublicationDate() != null) book.setPublicationDate(updateBook.getPublicationDate());
        if (updateBook.getIsbn() != null) book.setIsbn(updateBook.getIsbn());
        if (updateBook.getLanguage() != null) book.setLanguage(updateBook.getLanguage());
    }

    public static BookDTO mapToResponse(Book book) {
        return toDTO(book);
    }
}
