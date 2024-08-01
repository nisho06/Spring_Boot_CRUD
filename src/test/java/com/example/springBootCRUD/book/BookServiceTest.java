package com.example.springBootCRUD.book;

import com.example.springBootCRUD.exception.ApiRequestException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository);
    }

    @Test
    void testGetBooks() {
        bookService.getBooks();
        verify(bookRepository).findAll();
    }

    @Test
    void testRegisterBook() {
        String book_isbn = "123-15122-12";
        Book book = new Book(book_isbn,
                "Head First Java",
                "Nisho",
                2013,
                45.23);

        bookService.registerBook(book);
        verify(bookRepository).save(book);

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookArgumentCaptor.capture());
        Book capturedBook = bookArgumentCaptor.getValue();
        Assertions.assertEquals(book, capturedBook);
    }

    @Test
    void testThrowExceptionWhenIsbnTaken() {
        String book_isbn = "123-15122-12";
        Book book = new Book(book_isbn,
                "Head First Java",
                "Nisho",
                2013,
                45.23);

        given(bookRepository.findBookByIsbn(book_isbn)).willReturn(Optional.of(book));
        ApiRequestException exception = Assertions.assertThrows(
                ApiRequestException.class, () -> {bookService.registerBook(book);});
        Assertions.assertEquals("Book with ISBN - " + book_isbn + " already exists.", exception.getMessage());
        Assertions.assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;
        given(bookRepository.existsById(bookId)).willReturn(true);
        bookService.deleteBook(bookId);
        verify(bookRepository).deleteById(bookId);
    }

    @Test
    void throwExceptionWhenDeleteBook() {
        Long bookId = 1L;
        given(bookRepository.existsById(anyLong())).willReturn(false);
        ApiRequestException exception = Assertions.assertThrows(
                ApiRequestException.class, () -> {bookService.deleteBook(bookId);});
        Assertions.assertEquals("Book with id - " + bookId + " does not exist.", exception.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(bookRepository, never()).deleteById(any());
    }

    @Test
    void testUpdateBookIdNotExist() {

        Long bookId = 1L;
        String isbn = "11230-1235-1563";
        String name = "Java Book";
        String author = "Nishothan";
        Integer publicationYear = 2012;
        Double priceGbp = 12.52;

        given(bookRepository.findById(bookId)).willReturn(Optional.empty());

        ApiRequestException exception = Assertions.assertThrows(ApiRequestException.class, () -> {
            bookService.updateBook(bookId, isbn, name, author, publicationYear, priceGbp);
        });

        assertEquals("Book with id  " + bookId + " does not exist.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testUpdateBookIsbn(){
        Long bookId = 1L;
        String isbn = "11230-1235-1563";
        String name = "Java Book";
        String author = "Nishothan";
        Integer publicationYear = 2012;
        Double priceGbp = 12.52;

        Book book = new Book();

        given(bookRepository.findById(bookId)).willReturn(Optional.of(book));
        given(bookRepository.findBookByIsbn(isbn)).willReturn(Optional.empty());

        bookService.updateBook(bookId, isbn, name, author, publicationYear, priceGbp);

        assertEquals(isbn, book.getIsbn());
        assertEquals(name, book.getName());
        assertEquals(author, book.getAuthor());
        assertEquals(publicationYear, book.getPublicationYear());
        assertEquals(priceGbp, book.getPriceGbp());
    }

    @Test
    void testUpdateBookIsbnAlreadyExists(){
        Long bookId = 1L;
        String isbn = "11230-1235-1563";
        String name = "Java Book";
        String author = "Nishothan";
        Integer publicationYear = 2012;
        Double priceGbp = 12.52;

        Book book = new Book();

        given(bookRepository.findById(bookId)).willReturn(Optional.of(book));
        given(bookRepository.findBookByIsbn(isbn)).willReturn(Optional.of(book));

        ApiRequestException exception = Assertions.assertThrows(ApiRequestException.class, () -> {bookService.updateBook(bookId, isbn, name, author, publicationYear, priceGbp);});

        assertEquals("Book with ISBN " + isbn + " already exists.", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
    }

    @Test
    void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book();
        given(bookRepository.findById(bookId)).willReturn(Optional.of(book));

        Book actualBook = bookService.getBookById(bookId);

        assertEquals(book, actualBook);
    }

    @Test
    void testThrowExceptionWhenGetBookById() {
        Long bookId = 1L;
        given(bookRepository.findById(bookId)).willReturn(Optional.empty());

        ApiRequestException exception = Assertions.assertThrows(
                ApiRequestException.class, () -> {bookService.getBookById(bookId);});
        assertEquals("Book with id " + bookId + " does not exist.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}
