package com.example.springBootCRUD.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Find the book using ISBN.
     * @param isbn The ISBN of the book.
     * @return The book relevant to the specific ISBN.
     */
    Optional<Book> findBookByIsbn(String isbn);
}
