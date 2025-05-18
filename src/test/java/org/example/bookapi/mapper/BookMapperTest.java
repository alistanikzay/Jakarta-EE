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

}
