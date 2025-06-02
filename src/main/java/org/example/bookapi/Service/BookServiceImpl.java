package org.example.bookapi.Service;

import org.example.bookapi.Repository.BookRepository;
import org.example.bookapi.dto.BookDTO;
import org.example.bookapi.dto.CreateBookDTO;
import org.example.bookapi.dto.UpdateBookDTO;
import org.example.bookapi.entity.Book;
import org.example.bookapi.mapper.BookMapper;

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
        Book book = BookMapper.toEntity(dto);
        Book saved = bookRepository.save(book);
        return BookMapper.toDTO(saved);
    }

    @Override
    public BookDTO update(Long id, UpdateBookDTO dto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublicationDate(dto.getPublicationDate());
        book.setIsbn(dto.getIsbn());


        Book updatedBook = bookRepository.save(book);

        return BookMapper.toDTO(updatedBook);
    }

    @Override
    public List<BookDTO> getAll() {
        return bookRepository.findAll()
                .sorted((b1, b2) -> b1.getId().compareTo(b2.getId()))
                .map(book -> BookMapper.toDTO(book))
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
