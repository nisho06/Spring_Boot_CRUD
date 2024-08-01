package com.example.springBootCRUD.book;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @GetMapping(path = "{bookId}")
    public Book getBook(@PathVariable Long bookId) {
        return bookService.getBookById(bookId);
    }

    @PostMapping
    public void registerBookSingle(@RequestBody Book book) {
        bookService.registerBook(book);
    }

    @DeleteMapping(path="{bookId}")
    public void deleteBook(@PathVariable Long bookId){
        bookService.deleteBook(bookId);
    }

    @PutMapping(path = "{bookId}")
    public void updateBook(
            @PathVariable Long bookId,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer publicationYear,
            @RequestParam(required = false) Double priceGbp){
        bookService.updateBook(bookId, isbn, name, author, publicationYear, priceGbp);
    }
}
