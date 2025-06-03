package org.example.bookapi.mapper;

import jakarta.persistence.PersistenceException;
import org.example.bookapi.Exception.ConflictException;
import org.example.bookapi.Exception.DatabaseException;
import org.example.bookapi.Repository.BookRepository;
import org.example.bookapi.Service.BookServiceImpl;
import org.example.bookapi.dto.BookDTO;
import org.example.bookapi.dto.CreateBookDTO;
import org.example.bookapi.dto.UpdateBookDTO;
import org.example.bookapi.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("getById should return BookDTO when found")
    void getByIdShouldReturnBookDTOWhenFound() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book(
                bookId,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman om identitet, framtid och politik",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );
        book.setLanguage("Svenska");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        BookDTO result = bookService.getById(bookId);

        // Assert
        assertNotNull(result);
        assertEquals("Johannes Anyuru", result.author());
        assertEquals("De kommer att drunkna i sina mödrars tårar", result.title());
        assertEquals("9789113084075", result.isbn());
    }

    @Test
    @DisplayName("getById should throw RuntimeException when book is not found")
    void getByIdShouldThrowExceptionWhenBookNotFound() {
        // Arrange
        Long missingBookId = 99L;
        when(bookRepository.findById(missingBookId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.getById(missingBookId));
        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    @DisplayName("getAll should return list of BookDTOs")
    void getAllShouldReturnListOfBookDTOs() {
        // Arrange
        Book book1 = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman om identitet, framtid och politik",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );
        book1.setLanguage("Svenska");

        Book book2 = new Book(
                2L,
                "Aniara",
                "Harry Martinson",
                "Ett rymdepos om en skeppsresa utan mål",
                LocalDate.of(1956, 1, 1),
                "9789100123456"
        );
        book2.setLanguage("Svenska");

        when(bookRepository.findAll()).thenReturn(java.util.stream.Stream.of(book1, book2));

        // Act
        var result = bookService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Johannes Anyuru", result.get(0).author());
        assertEquals("Harry Martinson", result.get(1).author());
    }

    @Test
    @DisplayName("create should save and return BookDTO when input is valid")
    void createShouldSaveAndReturnBookDTO() {
        // Arrange
        CreateBookDTO createBookDTO = new CreateBookDTO(
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman om identitet, framtid och politik",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        Book bookEntity = new Book(
                1L,
                createBookDTO.title(),
                createBookDTO.author(),
                createBookDTO.description(),
                createBookDTO.publicationDate(),
                createBookDTO.isbn()
        );

        when(bookRepository.save(any(Book.class))).thenReturn(bookEntity);

        // Act
        BookDTO result = bookService.create(createBookDTO);

        // Assert
        assertNotNull(result);
        assertEquals("De kommer att drunkna i sina mödrars tårar", result.title());
        assertEquals("Johannes Anyuru", result.author());
        assertEquals("9789113084075", result.isbn());

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("create should throw ConflictException if book with same title and author already exists")
    void createShouldThrowConflictExceptionIfBookAlreadyExists() {
        // Arrange
        CreateBookDTO createBookDTO = new CreateBookDTO(
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman om identitet, framtid och politik",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        Book existingBook = new Book(
                1L,
                createBookDTO.title(),
                createBookDTO.author(),
                createBookDTO.description(),
                createBookDTO.publicationDate(),
                createBookDTO.isbn()
        );

        when(bookRepository.findAll()).thenReturn(java.util.stream.Stream.of(existingBook));

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class, () -> bookService.create(createBookDTO));
        assertEquals("A book with title: 'De kommer att drunkna i sina mödrars tårar' and author: 'Johannes Anyuru' already exists.", exception.getMessage());

        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("create should throw ConflictException if ISBN already exists")
    void createShouldThrowConflictExceptionIfIsbnAlreadyExists() {
        // Arrange
        CreateBookDTO createBookDTO = new CreateBookDTO(
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman om identitet, framtid och politik",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        Book existingBook = new Book(
                2L,
                "Annan bok med samma ISBN",
                "Annan författare",
                "Beskrivning",
                LocalDate.of(2020, 1, 1),
                "9789113084075"
        );

        when(bookRepository.findAll()).thenReturn(java.util.stream.Stream.of(existingBook));

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class, () -> bookService.create(createBookDTO));
        assertEquals("A book with ISBN: '9789113084075' already exists.", exception.getMessage());

        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("create should throw DatabaseException if database save fails")
    void createShouldThrowDatabaseExceptionIfSaveFails() {
        // Arrange
        CreateBookDTO createBookDTO = new CreateBookDTO(
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman om identitet, framtid och politik",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        // Anta att inga konflikter finns
        when(bookRepository.findAll()).thenReturn(java.util.stream.Stream.empty());

        // Simulera att databasen kastar ett undantag
        when(bookRepository.save(any(Book.class)))
                .thenThrow(new PersistenceException("DB error"));

        // Act & Assert
        DatabaseException exception = assertThrows(DatabaseException.class, () -> bookService.create(createBookDTO));
        assertEquals("A database error occurred during create book", exception.getMessage());

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("update book should successfully update existing book")
    void updateBookShouldSuccessfullyUpdateExistingBook() {
        // Arrange
        Long bookId = 1L;

        Book existingBook = new Book(
                bookId,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman om identitet, framtid och politik",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );
        existingBook.setLanguage("Svenska");

        UpdateBookDTO updateDTO = new UpdateBookDTO(
                "En annan titel",
                "En ny författare",
                "Uppdaterad beskrivning",
                LocalDate.of(2020, 5, 10),
                "9789123456789"
        );

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        // Act
        BookDTO result = bookService.update(bookId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updateDTO.getTitle(), result.title());
        assertEquals(updateDTO.getAuthor(), result.author());
        assertEquals(updateDTO.getIsbn(), result.isbn());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("update should throw NotFoundException if book with given ID does not exist")
    void updateShouldThrowNotFoundExceptionIfBookDoesNotExist() {
        // Arrange
        Long missingBookId = 42L;

        UpdateBookDTO updateDTO = new UpdateBookDTO(
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Uppdaterad beskrivning av boken",
                LocalDate.of(2020, 1, 1),
                "9789113084075"
        );

        when(bookRepository.findById(missingBookId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.update(missingBookId, updateDTO));
        assertEquals("Book not found", exception.getMessage());

        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("update should throw DatabaseException if database fails during update")
    void updateShouldThrowDatabaseExceptionIfDatabaseFailsDuringUpdate() {
        // Arrange
        Long bookId = 1L;

        Book existingBook = new Book(
                bookId,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman om identitet, framtid och politik",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );
        existingBook.setLanguage("Svenska");

        UpdateBookDTO updateDTO = new UpdateBookDTO(
                "Ny titel",
                "Ny författare",
                "Uppdaterad beskrivning",
                LocalDate.of(2022, 2, 2),
                "1234567890"
        );

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenThrow(new PersistenceException("DB error"));

        // Act & Assert
        DatabaseException exception = assertThrows(DatabaseException.class, () -> bookService.update(bookId, updateDTO));
        assertEquals("A database error occurred during update book", exception.getMessage());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(any(Book.class));
    }





}
