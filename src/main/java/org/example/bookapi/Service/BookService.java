package org.example.bookapi.Service;

import org.example.bookapi.dto.BookDTO;
import org.example.bookapi.dto.CreateBookDTO;
import org.example.bookapi.dto.UpdateBookDTO;

import java.util.List;

public interface BookService {
    BookDTO create(CreateBookDTO dto);
    BookDTO update(Long id, UpdateBookDTO dto);
    BookDTO getById(Long id);
    List<BookDTO> getAll();
    List<BookDTO> findByAuthor(String author);

    void delete(Long id);  // ← ändrad från Response till void
}
