package org.example.bookapi.mapper;

import org.example.bookapi.Repository.BookRepository;
import org.example.bookapi.Service.BookServiceImpl;
import org.example.bookapi.dto.BookDTO;
import org.example.bookapi.entity.Book;
import org.example.bookapi.Mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
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
public class BookServiceTest {

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




}
