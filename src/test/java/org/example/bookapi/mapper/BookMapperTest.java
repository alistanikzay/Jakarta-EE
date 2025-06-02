package org.example.bookapi.mapper;

import org.example.bookapi.Mapper.BookMapper;
import org.example.bookapi.Exception.ValidationException;
import org.example.bookapi.dto.BookDTO;
import org.example.bookapi.dto.CreateBookDTO;
import org.example.bookapi.dto.UpdateBookDTO;
import org.example.bookapi.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    @Test
    @DisplayName("Map CreateBookDTO to Book entity correctly")
    void shouldMapCreateBookDtoToEntity() {
        CreateBookDTO dto = new CreateBookDTO(
                "De kommer att drunkna i sina mödrars tårar", // title
                "Johannes Anyuru",                             // author
                "Roman",                                       // description
                LocalDate.of(2017, 3, 1),                      // publicationDate
                "9789113084075"                                // isbn
        );

        Book entity = BookMapper.toEntity(dto);

        assertEquals(dto.title(), entity.getTitle());
        assertEquals(dto.author(), entity.getAuthor());
        assertEquals(dto.description(), entity.getDescription());
        assertEquals(dto.publicationDate(), entity.getPublicationDate());
        assertEquals(dto.isbn(), entity.getIsbn());
    }

    @Test
    @DisplayName("Map Book entity to BookDTO correctly")
    void shouldMapEntityToBookDto() {
        Book book = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar", // title
                "Johannes Anyuru",                             // author
                "Roman",                                       // description
                LocalDate.of(2017, 3, 1),                      // publicationDate
                "9789113084075"                                // isbn
        );

        BookDTO dto = BookMapper.toDTO(book);

        assertEquals(book.getTitle(), dto.title());
        assertEquals(book.getAuthor(), dto.author());
        assertEquals(book.getDescription(), dto.description());
        assertEquals(book.getPublicationDate(), dto.publicationDate());
        assertEquals(book.getIsbn(), dto.isbn());
    }

    @Test
    @DisplayName("Map to entity update should not throw exception when book is not null")
    void shouldNotThrowWhenUpdatingEntity() {
        Book existingBook = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        UpdateBookDTO updateBookDTO = new UpdateBookDTO(
                "Ny titel",
                "Ny författare",
                "Ny beskrivning",
                LocalDate.of(2020, 1, 1),
                "1234567890"
        );

        assertDoesNotThrow(() -> BookMapper.updateEntity(updateBookDTO, existingBook));
    }

    @Test
    @DisplayName("Map to entity update should throw validation exception when UpdateBookDTO is null")
    void shouldThrowValidationExceptionWhenUpdateDtoIsNull() {
        Book existingBook = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        ValidationException exception = assertThrows(ValidationException.class, () ->
                BookMapper.updateEntity(null, existingBook)
        );

        assertEquals("UpdateBookDTO cannot be null when updating an entity.", exception.getMessage());
    }

    @Test
    @DisplayName("Map to entity update should not update author when it is null")
    void shouldNotUpdateAuthorWhenNullInUpdateDto() {
        Book existingBook = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        UpdateBookDTO updateBookDTO = new UpdateBookDTO(
                "Ny titel",
                null, // Författare saknas här
                "Ny beskrivning",
                LocalDate.of(2020, 1, 1),
                "1234567890"
        );

        BookMapper.updateEntity(updateBookDTO, existingBook);

        // Förväntat: författare ska vara oförändrad
        assertEquals("Johannes Anyuru", existingBook.getAuthor());
    }

    @Test
    @DisplayName("Map to entity update should update author when it is not null")
    void shouldUpdateAuthorWhenNotNullInUpdateDto() {
        Book existingBook = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        UpdateBookDTO updateBookDTO = new UpdateBookDTO(
                "Ny titel",
                "Ny författare", // Här är författare INTE null
                "Ny beskrivning",
                LocalDate.of(2020, 1, 1),
                "1234567890"
        );

        BookMapper.updateEntity(updateBookDTO, existingBook);

        // Förväntat: författaren ska ha uppdaterats
        assertEquals("Ny författare", existingBook.getAuthor());
    }

    @Test
    @DisplayName("Map to entity update should not update title when it is null")
    void shouldNotUpdateTitleWhenNullInUpdateDto() {
        Book existingBook = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        UpdateBookDTO updateBookDTO = new UpdateBookDTO(
                null, // Titeln är null
                "Ny författare",
                "Ny beskrivning",
                LocalDate.of(2020, 1, 1),
                "1234567890"
        );

        BookMapper.updateEntity(updateBookDTO, existingBook);

        // Förväntat: titeln ska vara oförändrad
        assertEquals("De kommer att drunkna i sina mödrars tårar", existingBook.getTitle());
    }

    @Test
    @DisplayName("Map to entity update should update title when it is not null")
    void shouldUpdateTitleWhenNotNullInUpdateDto() {
        Book existingBook = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        UpdateBookDTO updateBookDTO = new UpdateBookDTO(
                "De drunknade", // ny titel som inte är null
                "Johannes Anyuru",
                "Reviderad beskrivning",
                LocalDate.of(2023, 5, 15),
                "1112223334"
        );

        BookMapper.updateEntity(updateBookDTO, existingBook);

        // Förväntat: titeln ska uppdateras
        assertEquals("De drunknade", existingBook.getTitle());
    }

    @Test
    @DisplayName("Map to entity update should not update description when it is null")
    void shouldNotUpdateDescriptionWhenNullInUpdateDto() {
        Book existingBook = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Originalbeskrivning",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        UpdateBookDTO updateBookDTO = new UpdateBookDTO(
                "Ny titel",
                "Ny författare",
                null, // beskrivning är null
                LocalDate.of(2020, 1, 1),
                "1234567890"
        );

        BookMapper.updateEntity(updateBookDTO, existingBook);

        // Förväntat: beskrivningen ska inte ha ändrats
        assertEquals("Originalbeskrivning", existingBook.getDescription());
    }

    @Test
    @DisplayName("Map to entity update should update description when it is not null")
    void shouldUpdateDescriptionWhenNotNullInUpdateDto() {
        Book existingBook = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Originalbeskrivning",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        UpdateBookDTO updateBookDTO = new UpdateBookDTO(
                "De drunknade",
                "Johannes Anyuru",
                "Reviderad beskrivning", // ny beskrivning som inte är null
                LocalDate.of(2023, 5, 15),
                "1112223334"
        );

        BookMapper.updateEntity(updateBookDTO, existingBook);

        // Förväntat: beskrivningen ska uppdateras
        assertEquals("Reviderad beskrivning", existingBook.getDescription());
    }

    @Test
    @DisplayName("Map to entity update should not update ISBN when it is null")
    void shouldNotUpdateIsbnWhenNullInUpdateDto() {
        Book existingBook = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman",
                LocalDate.of(2017, 3, 1),
                "9789113084075" // original ISBN
        );

        UpdateBookDTO updateBookDTO = new UpdateBookDTO(
                "Ny titel",
                "Ny författare",
                "Ny beskrivning",
                LocalDate.of(2020, 1, 1),
                null // ISBN är null
        );

        BookMapper.updateEntity(updateBookDTO, existingBook);

        // ISBN ska vara oförändrad
        assertEquals("9789113084075", existingBook.getIsbn());
    }

    @Test
    @DisplayName("Map to entity update should update ISBN when it is not null")
    void shouldUpdateIsbnWhenNotNullInUpdateDto() {
        Book existingBook = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );

        UpdateBookDTO updateBookDTO = new UpdateBookDTO(
                "Ny titel",
                "Ny författare",
                "Ny beskrivning",
                LocalDate.of(2022, 12, 1),
                "9998887776" // Nytt ISBN, ej null
        );

        BookMapper.updateEntity(updateBookDTO, existingBook);

        // Förväntat: ISBN ska uppdateras
        assertEquals("9998887776", existingBook.getIsbn());
    }

    @Test
    @DisplayName("Map to entity update should not update language when it is null")
    void shouldNotUpdateLanguageWhenNullInUpdateDto() {
        Book existingBook = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );
        existingBook.setLanguage("Svenska");

        UpdateBookDTO updateBookDTO = new UpdateBookDTO(
                "Ny titel",
                "Ny författare",
                "Ny beskrivning",
                LocalDate.of(2020, 1, 1),
                "1234567890"
                // language saknas eller sätts till null
        );

        BookMapper.updateEntity(updateBookDTO, existingBook);

        assertEquals("Svenska", existingBook.getLanguage());
    }

    @Test
    @DisplayName("Map to entity update should update language when it is not null")
    void shouldUpdateLanguageWhenNotNullInUpdateDto() {
        Book existingBook = new Book(
                1L,
                "De kommer att drunkna i sina mödrars tårar",
                "Johannes Anyuru",
                "Roman",
                LocalDate.of(2017, 3, 1),
                "9789113084075"
        );
        existingBook.setLanguage("Svenska");

        UpdateBookDTO updateBookDTO = new UpdateBookDTO(
                "Ny titel",
                "Ny författare",
                "Ny beskrivning",
                LocalDate.of(2020, 1, 1),
                "1234567890"
        );
        updateBookDTO.setLanguage("Engelska");

        BookMapper.updateEntity(updateBookDTO, existingBook);

        assertEquals("Engelska", existingBook.getLanguage());
    }



}
