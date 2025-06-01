package org.example.bookapi.mapper;

import org.example.bookapi.dto.BookDTO;
import org.example.bookapi.dto.CreateBookDTO;
import org.example.bookapi.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BookMapperTest {


    @Test
    public void testToEntity() {
        CreateBookDTO dto = new CreateBookDTO("Java", "Oracle", "A guide", LocalDate.of(2010, 5, 10), "1234567890");
        Book book = BookMapper.toEntity(dto);

        Assertions.assertEquals("Java", book.getTitle());
        Assertions.assertEquals("Oracle", book.getAuthor());
    }

    @Test
    public void testToDTO() {
        Book book = new Book(5L, "Effective Java", "Joshua Bloch", "Great book", LocalDate.of(2008, 1, 1), "1234567890123");
        BookDTO dto = BookMapper.toDTO(book);

        Assertions.assertEquals(5L, dto.getId());
        Assertions.assertEquals("Effective Java", dto.getTitle());
    }

    @Test
    public void testToEntity_AllFieldsMappedCorrectly() {
        CreateBookDTO dto = new CreateBookDTO("Java", "Oracle", "A guide", LocalDate.of(2010, 5, 10), "1234567890");
        Book book = BookMapper.toEntity(dto);

        Assertions.assertEquals("Java", book.getTitle());
        Assertions.assertEquals("Oracle", book.getAuthor());
        Assertions.assertEquals("A guide", book.getDescription());
        Assertions.assertEquals(LocalDate.of(2010, 5, 10), book.getPublished());
        Assertions.assertEquals("1234567890", book.getIsbn());
    }

    @Test
    public void testToDTO_AllFieldsMappedCorrectly() {
        Book book = new Book(5L, "Effective Java", "Joshua Bloch", "Great book", LocalDate.of(2008, 1, 1), "1234567890123");
        BookDTO dto = BookMapper.toDTO(book);

        Assertions.assertEquals(5L, dto.getId());
        Assertions.assertEquals("Effective Java", dto.getTitle());
        Assertions.assertEquals("Joshua Bloch", dto.getAuthor());
        Assertions.assertEquals("Great book", dto.getDescription());
        Assertions.assertEquals(LocalDate.of(2008, 1, 1), dto.getPublished());
        Assertions.assertEquals("1234567890123", dto.getIsbn());
    }

}
