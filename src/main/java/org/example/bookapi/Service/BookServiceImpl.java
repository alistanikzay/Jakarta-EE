package org.example.bookapi.Service;

import org.example.bookapi.Exception.ConflictException;
import org.example.bookapi.Exception.DatabaseException;
import org.example.bookapi.Repository.BookRepository;
import org.example.bookapi.dto.BookDTO;
import org.example.bookapi.dto.CreateBookDTO;
import org.example.bookapi.dto.UpdateBookDTO;
import org.example.bookapi.entity.Book;
import org.example.bookapi.Mapper.BookMapper;

import jakarta.ws.rs.core.Response;
import java.util.Map;


import jakarta.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDTO getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return BookMapper.toDTO(book);
    }

    @Override
    public BookDTO create(CreateBookDTO dto) {
        // Hämta alla böcker en gång och spara i en lista
        List<Book> existingBooks = bookRepository.findAll().toList();

        // Kontrollera om bok med samma titel och författare redan finns
        boolean titleAuthorExists = existingBooks.stream()
                .anyMatch(book ->
                        book.getTitle().equalsIgnoreCase(dto.title()) &&
                                book.getAuthor().equalsIgnoreCase(dto.author())
                );

        if (titleAuthorExists) {
            throw new ConflictException("A book with title: '" + dto.title() +
                    "' and author: '" + dto.author() + "' already exists.");
        }

        // Kontrollera om bok med samma ISBN redan finns
        boolean isbnExists = existingBooks.stream()
                .anyMatch(book -> book.getIsbn().equals(dto.isbn()));

        if (isbnExists) {
            throw new ConflictException("A book with ISBN: '" + dto.isbn() + "' already exists.");
        }

        try {
            Book book = BookMapper.toEntity(dto);
            Book saved = bookRepository.save(book);
            return BookMapper.toDTO(saved);
        } catch (PersistenceException e) {
            throw new DatabaseException("A database error occurred during create book", e);
        }
    }

    @Override
    public BookDTO update(Long id, UpdateBookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublicationDate(dto.getPublicationDate());
        book.setIsbn(dto.getIsbn());

        try {
            Book updatedBook = bookRepository.save(book);
            return BookMapper.toDTO(updatedBook);
        } catch (PersistenceException e) {
            throw new DatabaseException("A database error occurred during update book", e);
        }
    }


    @Override
    public List<BookDTO> getAll() {
        return bookRepository.findAll()
                .sorted((b1, b2) -> b1.getId().compareTo(b2.getId()))
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findByAuthor(String author) {
        return bookRepository.findAll()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        try {
            bookRepository.delete(book);
        } catch (PersistenceException e) {
            throw new DatabaseException("A database error occurred during delete book", e);
        }
    }

}
