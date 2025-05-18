package org.example.bookapi.Repository;

import jakarta.data.repository.BasicRepository;
import org.example.bookapi.entity.Book;

public interface BookRepository extends BasicRepository<Book, Long> {

}
