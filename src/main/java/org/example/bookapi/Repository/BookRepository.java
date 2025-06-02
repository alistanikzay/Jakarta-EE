package org.example.bookapi.Repository;

import jakarta.data.repository.BasicRepository;
import org.example.bookapi.entity.Book;
import java.util.List;

public interface BookRepository extends BasicRepository<Book, Long> {

    <S extends Book> S save(S entity);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(String author, String title);


}
