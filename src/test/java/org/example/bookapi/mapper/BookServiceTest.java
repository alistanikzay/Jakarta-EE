package org.example.bookapi.mapper;

import org.example.bookapi.Repository.BookRepository;
import org.example.bookapi.Service.BookService;
import org.example.bookapi.Service.BookServiceImpl;
import org.example.bookapi.dto.BookDTO;
import org.example.bookapi.dto.CreateBookDTO;
import org.example.bookapi.dto.UpdateBookDTO;
import org.example.bookapi.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    public void testCreate() {
        CreateBookDTO dto = new CreateBookDTO("Title", "Author", 2023);
        Book book = BookMapper.toEntity(dto);
        Book savedBook = new Book(1L, "Title", "Author", 2023);

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        BookDTO result = bookService.create(dto);

        assertEquals("Title", result.title());
        assertEquals("Author", result.author());
        assertEquals(2025, result.releaseYear());
    }

    @Test
    public void testGetById() {
        Book book = new Book(1L, "Title", "Author", 2023);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDTO result = bookService.getById(1L);

        assertEquals("Title", result.title());
    }

    @Test
    public void testGetAll() {
        List<Book> books = Arrays.asList(
                new Book(1L, "Book A", "Author A", 2020),
                new Book(2L, "Book B", "Author B", 2021)
        );
        // Returnera Stream<Book> istället för List<Book>
        when(bookRepository.findAll()).thenReturn(books.stream());

        List<BookDTO> result = bookService.getAll();

        assertEquals(2, result.size());
        assertEquals("Book A", result.get(0).title());
    }

    @Test
    public void testUpdate() {
        Book book = new Book(1L, "Old Title", "Old Author", 2020);
        UpdateBookDTO dto = new UpdateBookDTO("New Title", "New Author", "Description", LocalDate.of(2025, 1, 1), "1234567890");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookDTO result = bookService.update(1L, dto);

        System.out.println("Result title: " + result.title()); 

        assertEquals("New Title", result.title());
    }


    @Test
    public void testDelete() {
        doNothing().when(bookRepository).deleteById(1L);
        bookService.delete(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }


    @Test
    public void testFindById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.getById(1L));
    }
}
