package com.example.springBootCRUD.book;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    void cleanDatabase(){
        bookRepository.deleteAll();
    }

    @Test
    void itShouldFindBookWhichExistsByIsbn() {
        String book_isbn = "123-15122-12";
        String book_name = "Head First Java";
        String book_author = "Nisho";
        int book_publication_year = 2013;
        double book_price = 45.23;

        Book book = new Book(book_isbn,
                book_name,
                book_author,
                book_publication_year,
                book_price);
        bookRepository.save(book);

        Optional<Book> optionalBook =  bookRepository.findBookByIsbn(book_isbn);
        assertTrue(optionalBook.isPresent());

        Book findBookFromOptional = optionalBook.orElse(null);
        Assertions.assertEquals(findBookFromOptional, book);
    }

    @Test
    void itShouldFindBookWhichDoesNotExistByIsbn() {
        String book_isbn = "123-15122-12";

        Optional<Book> optionalBook =  bookRepository.findBookByIsbn(book_isbn);
        Book findBookFromOptional = optionalBook.orElse(null);

        assertFalse(optionalBook.isPresent());
        assertNull(findBookFromOptional);
    }
}