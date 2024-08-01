package com.example.springBootCRUD.book;

import com.example.springBootCRUD.exception.ApiRequestException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieve the whole list of books available.
     * @return The whole list of books.
     */
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieve the book using the book ID.
     * @param bookId The ID of the book.
     * @return The book relevant to the specific ID.
     */
    public Book getBookById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()){
            throw new ApiRequestException("Book with id " + bookId + " does not exist.",
                    HttpStatus.NOT_FOUND);
        }
        return book.get();
    }

    /**
     * Add a book to the database.
     * @param book The book which needs to be added to the database.
     */
    public void registerBook(Book book) {
        Optional<Book> bookOptional = bookRepository.findBookByIsbn(book.getIsbn());
        if (bookOptional.isPresent()) {
            throw new ApiRequestException("Book with ISBN - " + book.getIsbn() + " already exists.",
                    HttpStatus.CONFLICT);
        }
        bookRepository.save(book);
    }

    /**
     * Delete a book from the database using the book ID.
     * @param bookId The ID of the book.
     */
    public void deleteBook(Long bookId) {
        boolean isBookExist = bookRepository.existsById(bookId);
        if (!isBookExist){
            throw new ApiRequestException("Book with id - " + bookId + " does not exist.",
                    HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(bookId);
    }

    /**
     * Update the details of the book as per the given details.
     * @param bookId The ID of the book.
     * @param isbn The ISBN of the book.
     * @param name The name of the book.
     * @param author The author of the book.
     * @param publicationYear The publication year of the book.
     * @param priceGbp The price of the book in pounds.
     */
    @Transactional
    public void updateBook(Long bookId, String isbn, String name, String author, Integer publicationYear, Double priceGbp) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ApiRequestException(
                "Book with id  " + bookId + " does not exist.", HttpStatus.NOT_FOUND));
        if (isbn != null && isbn.length()>0 && !(Objects.equals(isbn, book.getIsbn()))){
            if (bookRepository.findBookByIsbn(isbn).isPresent()){
                throw new ApiRequestException("Book with ISBN " + isbn + " already exists.",
                        HttpStatus.CONFLICT);
            }
            book.setIsbn(isbn);
        }

        if (name != null && name.length()>0 && !(Objects.equals(name, book.getName()))){
            book.setName(name);
        }

        if (author != null && author.length()>0 && !(Objects.equals(author, book.getAuthor()))){
            book.setAuthor(author);
        }

        if (publicationYear != null && publicationYear > 0 && !(Objects.equals(publicationYear,
                book.getPublicationYear()))){
            book.setPublicationYear(publicationYear);
        }

        if (priceGbp != null && priceGbp > 0 && !(Objects.equals(priceGbp, book.getPriceGbp()))){
            book.setPriceGbp(priceGbp);
        }
    }
}
