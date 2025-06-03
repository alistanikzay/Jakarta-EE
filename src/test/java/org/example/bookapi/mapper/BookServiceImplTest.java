package org.example.bookapi.mapper;

import org.example.bookapi.Exception.ConflictException;
import org.example.bookapi.Repository.BookRepository;
import org.example.bookapi.Service.BookServiceImpl;
import org.example.bookapi.dto.BookDTO;
import org.example.bookapi.dto.CreateBookDTO;
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



}
